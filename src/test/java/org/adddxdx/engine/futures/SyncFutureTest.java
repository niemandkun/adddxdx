/*
 * Copyright (C) 2017
 * This file is part of adddxdx.
 *
 * adddxdx is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * adddxdx is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with adddxdx.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.adddxdx.engine.futures;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;

public class SyncFutureTest {
    private final static boolean DEBUG = true;

    private <T> T loopUntilNotDone(Future<T> future) throws ExecutionException, InterruptedException {
        int loopc = 0;
        int counter = 0;
        while (!(future.isDone() || future.isCancelled())) {
            int executed = SyncFuture.DEFAULT_DISPATCHER.dispatch(Duration.ofSeconds(0), 10L);
            counter += executed;
            if (executed > 0) {
                loopc++;
            }
        }

        if (DEBUG) {
            System.out.printf("Finished in %s iterations\n", loopc);
            System.out.printf("%s synchroneous tasks executed\n", counter);
            System.out.printf("Output: %s\n", future.get());
        }
        return future.get();
    }

    class TestException extends RuntimeException {

        TestException(String message) {
            super(message);
        }
    }

    @Test
    public void exceptionallyTest() throws Exception {
        CompletableFuture<Integer> f1 = new CompletableFuture<>();
        f1.completeExceptionally(new RuntimeException());
        CompletableFuture<Integer> e1 = f1.exceptionally(throwable -> 1);

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> e2 = f2.exceptionally(throwable -> 1);

        assert 1 == loopUntilNotDone(e1);
        assert 10 == loopUntilNotDone(e2);
    }

    @Test
    public void chainMethodsTest() throws Exception {
        SyncFuture<Integer> future = SyncFuture.completedFuture(0);

        SyncFuture<Integer> f1 = future.thenApply(i -> i + 1);
        SyncFuture<Integer> f2 = future.thenApply(i -> i + 2);
        f1.thenRun(() -> System.out.println("thenRun"));
        f2.thenAccept(i -> System.out.printf("thenAccept(%s)\n", i));

        SyncFuture<Integer> f3 = f1.thenCombine(f2, (a, b) -> a * 100 + b); // 102
        SyncFuture<Integer> f4 = f2.thenCombine(f1, (a, b) -> a * 100 + b); // 201
        SyncFuture<Void> f5 = f1.acceptEither(f2, new Consumer<Integer>() {
            private int runCounter = 0;

            @Override
            public void accept(Integer a) {
                assert a == 1 || a == 2;
                if (runCounter++ > 0) {
                    Assert.fail();
                }
            }
        });

        f1.runAfterEither(f2, new Runnable() {
            private int runCounter = 0;

            @Override
            public void run() {
                System.out.println("runAfterEither");
                if (runCounter++ > 0) {
                    Assert.fail();
                }
            }
        });


        SyncFuture<Void> f6 = f5.runAfterBoth(f4, () -> System.out.println("runAfterBoth"));
        SyncFuture<Void> f7 = f3.thenAcceptBoth(f4, (_102, _201) -> {
            assertTrue(102 == _102);
            assertTrue(201 == _201);
            System.out.println("thenAcceptBoth");
        });

        SyncFuture<Integer> f8 = f1.applyToEither(f3, integer -> {
            throw new TestException("planned");
        });

        SyncFuture<Integer> f9 = f8.exceptionally(throwable -> {
            Assert.assertEquals(TestException.class, throwable.getCause().getClass());
            return -1;
        });

        SyncFuture<Void> f99 = f7.thenCompose(aVoid -> f6.thenCompose(aVoid1 -> f6.thenCompose(aVoid3 -> f6)));


        loopUntilNotDone(f99);
        assert 102 == loopUntilNotDone(f3);
        assert 201 == loopUntilNotDone(f4);
        assert -1 == loopUntilNotDone(f9);
        loopUntilNotDone(f5);
        loopUntilNotDone(f6);
        loopUntilNotDone(f7);
    }

    @Test
    public void whenCompleteTest() throws Exception {
        System.out.println(Thread.currentThread().getName());

        SyncFuture<Integer> future = SyncFuture.supply(() -> 10);

        SyncFuture<Integer> future0 = future
                .thenApply(i -> i + 1)
                .whenComplete((integer, throwable) -> {
                    assertTrue(integer != null && integer == 11);
                    System.out.print("Completed normaly ");
                    System.out.println(Thread.currentThread().getName());
                });

        SyncFuture<Integer> future1 = future.thenApply(i -> {
            if (true) {
                throw new TestException("woooooop");
            }
            else {
                return i + 1;
            }
        }).whenComplete((o, throwable) -> {
            assertTrue(throwable != null);
            System.out.print("Completed exceptionaly ");
            System.out.println(Thread.currentThread().getName());
        }).exceptionally(t -> 11);

        assert 11 == loopUntilNotDone(future0);
        assert 11 == loopUntilNotDone(future1);
    }

    @Test
    public void handleTest() throws Exception {
        System.out.println(Thread.currentThread().getName());

        SyncFuture<Integer> future = SyncFuture.supply(() -> 10);

        SyncFuture<Integer> future0 = future
                .thenApply(i -> i + 1)
                .handle((integer, throwable) -> {
                    assertTrue(integer != null && integer == 11);
                    System.out.print("Completed normaly ");
                    System.out.println(Thread.currentThread().getName());
                    return integer;
                });

        SyncFuture<String> future1 = future.thenApply(i -> {
            if (true)  {
                throw new TestException("woooooop");
            }
            else {
                return i + 1;
            }
        }).handle((o, throwable) -> {
            assertTrue(throwable != null);
            System.out.print("Completed exceptionaly ");
            System.out.println(Thread.currentThread().getName());
            return "Hello";
        });

        assert 11 == loopUntilNotDone(future0);
        assert Objects.equals("Hello", loopUntilNotDone(future1));
    }

    @Test
    public void basicUsageTest() throws ExecutionException, InterruptedException {

        SyncFuture<Integer> future = SyncFuture.supply(() -> 10).thenApplyAsync(d -> {
            System.out.println(d.getClass());
            return d.toString();
        }).thenApply(s -> {
            System.out.println(s.getClass());
            return 1;
        });

        assert 1 == loopUntilNotDone(future);
    }

    @Test(expected = CancellationException.class)
    public void cancelTest() throws Throwable {
        SyncFuture<Integer> future = SyncFuture.supply(() -> 10);
        future = future.thenApply(i -> i + 1);

        boolean cancel = future.cancel(false);
        assert cancel : "test suite assumes that cancel was succed";

        SyncFuture<Integer> neverRunned = future.thenApply(i -> {
            Assert.fail();
            return i + 1;
        });

        try {
            loopUntilNotDone(neverRunned);
        }
        catch (ExecutionException e) {
            throw e.getCause();
        }
        Assert.fail();
    }


    @Test
    public void syncAsyncTreeTopologyRecallsTest() throws Exception {

        SyncFuture<Float> future = SyncFuture.completedFuture(1f)
                .thenApplyAsync(i -> i * 10)
                .thenApply(i -> i / 10);


        SyncFuture<Float> f1 = future
                .thenApplyAsync(i -> i * 10)
                .thenApply(i -> i / 10)
                .thenApplyAsync(i -> i * 100)
                .thenApply(i -> i / 10);

        SyncFuture<Float> f2 = future
                .thenApplyAsync(i -> i * 11)
                .thenApply(i -> i / 10);

        assert 10f == loopUntilNotDone(f1);
        assert 1.1f == loopUntilNotDone(f2);
    }

    @Test
    public void chainedSyncroneousFunctionsTest() throws ExecutionException, InterruptedException {
        SyncFuture<Integer> future = SyncFuture.supply(() -> 10)
                .thenApply(d -> d * 10)
                .thenApply(d -> d * 10)
                .thenApply(d -> d * 10)
                .thenApply(d -> d * 10);

        assert 100000 == loopUntilNotDone(future);
    }

    private CompletableFuture<Integer> futureRecursion(CompletableFuture<Integer> cf) {
        return cf.thenComposeAsync(integer -> {
            if (integer > 1000) {
                return CompletableFuture.completedFuture(integer);
            }
            return futureRecursion(cf.thenApplyAsync(r -> r + 1));
        });
    }

    @Test
    public void futureRecursionExampleTest() throws Exception {
        CompletableFuture<Integer> f2 = futureRecursion(CompletableFuture.completedFuture(1));
        assert 1001 == loopUntilNotDone(f2);
    }

}