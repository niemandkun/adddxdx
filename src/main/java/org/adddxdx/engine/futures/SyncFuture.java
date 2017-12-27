/*
 * Copyright (C) 2017 Ostanin Igor
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

import java.util.concurrent.*;
import java.util.function.*;

public class SyncFuture<T> implements Future<T>, CompletionStage<T> {
    static final SyncDispatcher DEFAULT_DISPATCHER = new SyncDispatcher();

    private final TaskQueueDispatcher mTaskQueue;
    private final CompletableFuture<T> mInnerFuture;

    public static <T> SyncFuture<T> supply(Supplier<T> supplier) {
        SyncFuture<T> future = new SyncFuture<>(DEFAULT_DISPATCHER);
        DEFAULT_DISPATCHER.enqueue(() -> future.complete(supplier.get()));
        return future;
    }

    public static <T> SyncFuture<T> supplyAsync(Supplier<T> supplier) {
        return new SyncFuture<>(DEFAULT_DISPATCHER, CompletableFuture.supplyAsync(supplier));
    }

    public static <T> SyncFuture<T> completedFuture(T result) {
        return new SyncFuture<>(DEFAULT_DISPATCHER, result);
    }

    public static SyncDispatcher getDefaultDispatcher() {
        return DEFAULT_DISPATCHER;
    }

    public SyncFuture(TaskQueueDispatcher taskDispatcher, T object) {
        this(taskDispatcher, CompletableFuture.completedFuture(object));
    }

    public SyncFuture(TaskQueueDispatcher taskDispatcher) {
        this(taskDispatcher, new CompletableFuture<>());
    }

    private SyncFuture(TaskQueueDispatcher taskDispatcher, CompletableFuture<T> toWrap) {
        mTaskQueue = taskDispatcher;
        mInnerFuture = toWrap;
    }

    private <U> Runnable complete(SyncFuture<U> future, Runnable withAction) {
        return complete(future, () -> { withAction.run(); return null; });
    }

    private <U> Runnable complete(CompletableFuture<U> future, Runnable withAction) {
        return complete(future, () -> { withAction.run(); return null; });
    }

    private <U> Runnable complete(SyncFuture<U> future, Supplier<U> withResult) {
        return () -> {
            try {
                future.complete(withResult.get());
            }
            catch (Throwable t) {
                future.completeExceptionally(t);
            }
        };
    }

    private <U> Runnable complete(CompletableFuture<U> future, Supplier<U> action) {
        return () -> {
            try {
                future.complete(action.get());
            }
            catch (Throwable t) {
                future.completeExceptionally(t);
            }
        };
    }

    public boolean complete(T object) {
        return mInnerFuture.complete(object);
    }

    public boolean completeExceptionally(Throwable ex) {
        return mInnerFuture.completeExceptionally(ex);
    }

    @Override
    public <U> SyncFuture<U> thenApply(Function<? super T, ? extends U> fn) {
        SyncFuture<U> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.thenAccept(res -> mTaskQueue.enqueue(complete(future, () -> fn.apply(res))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public <U> SyncFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenApplyAsync(fn));
    }

    @Override
    public <U> SyncFuture<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenApplyAsync(fn, executor));
    }

    @Override
    public SyncFuture<Void> thenAccept(Consumer<? super T> action) {
        SyncFuture<Void> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.thenAccept(res -> mTaskQueue.enqueue(complete(future, () -> action.accept(res))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public SyncFuture<Void> thenAcceptAsync(Consumer<? super T> action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenAcceptAsync(action));
    }

    @Override
    public SyncFuture<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenAcceptAsync(action, executor));
    }

    @Override
    public SyncFuture<Void> thenRun(Runnable action) {
        SyncFuture<Void> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.thenRun(() -> mTaskQueue.enqueue(complete(future, action)));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public SyncFuture<Void> thenRunAsync(Runnable action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenRunAsync(action));
    }

    @Override
    public SyncFuture<Void> thenRunAsync(Runnable action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenRunAsync(action, executor));
    }

    @Override
    public <U, V> SyncFuture<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        SyncFuture<V> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.thenAcceptBoth(other, (t, u) -> mTaskQueue.enqueue(complete(future, () -> fn.apply(t, u))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public <U, V> SyncFuture<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCombineAsync(other, fn));
    }

    @Override
    public <U, V> SyncFuture<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCombineAsync(other, fn, executor));
    }

    @Override
    public <U> SyncFuture<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        SyncFuture<Void> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.thenAcceptBoth(other, (t, u) -> mTaskQueue.enqueue(complete(future, () -> action.accept(t, u))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public <U> SyncFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenAcceptBothAsync(other, action));
    }

    @Override
    public <U> SyncFuture<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenAcceptBothAsync(other, action, executor));
    }

    @Override
    public SyncFuture<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        SyncFuture<Void> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.runAfterBoth(other, () -> mTaskQueue.enqueue(complete(future, action)));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public SyncFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.runAfterBothAsync(other, action));
    }

    @Override
    public SyncFuture<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.runAfterBothAsync(other, action, executor));
    }

    @Override
    public <U> SyncFuture<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        SyncFuture<U> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.acceptEither(other, (res) -> mTaskQueue.enqueue(complete(future, () -> fn.apply(res))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public <U> SyncFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.applyToEitherAsync(other, fn));
    }

    @Override
    public <U> SyncFuture<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.applyToEitherAsync(other, fn, executor));
    }

    @Override
    public SyncFuture<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        SyncFuture<Void> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.acceptEither(other, (res) -> mTaskQueue.enqueue(complete(future, () -> action.accept(res))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public SyncFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.acceptEitherAsync(other, action));
    }

    @Override
    public SyncFuture<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.acceptEitherAsync(other, action, executor));
    }

    @Override
    public SyncFuture<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        SyncFuture<Void> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.runAfterEither(other, () -> mTaskQueue.enqueue(complete(future, action)));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public SyncFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.runAfterEitherAsync(other, action));
    }

    @Override
    public SyncFuture<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.runAfterEitherAsync(other, action, executor));
    }

    @Override
    public <U> SyncFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        CompletableFuture<CompletionStage<U>> dummy = new CompletableFuture<>();
        CompletableFuture<U> composed = dummy.thenCompose(nextFuture -> nextFuture);
        SyncFuture<U> future = new SyncFuture<>(mTaskQueue, composed);
        mInnerFuture.thenAccept(t -> mTaskQueue.enqueue(complete(dummy, () -> fn.apply(t))));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public <U> SyncFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenComposeAsync(fn));
    }

    @Override
    public <U> SyncFuture<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenComposeAsync(fn));
    }

    @Override
    public SyncFuture<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.exceptionally(fn));
    }

    @Override
    public SyncFuture<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        SyncFuture<T> future = new SyncFuture<>(mTaskQueue);
        mInnerFuture.whenComplete((r, e) -> mTaskQueue.enqueue(() -> {
            try {
                action.accept(r, e);
            }
            catch (Throwable t) {
                if (e == null) {
                    future.completeExceptionally(t);
                }
                else {
                    future.completeExceptionally(e);
                }
            }
            if (!future.isDone()) {
                future.complete(r);
            }
        }));
        return new SyncFuture<>(mTaskQueue, mInnerFuture.thenCompose(t -> future));
    }

    @Override
    public SyncFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.whenCompleteAsync(action));
    }

    @Override
    public SyncFuture<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.whenCompleteAsync(action, executor));
    }

    @Override
    public <U> SyncFuture<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        SyncFuture<U> future = new SyncFuture<>(mTaskQueue);
        CompletableFuture<Void> handle = mInnerFuture.handle((r, e) -> {
            mTaskQueue.enqueue(() -> {
                U result = null;
                try {
                    result = fn.apply(r, e);
                }
                catch (Throwable t) {
                    if (e == null) {
                        future.completeExceptionally(t);
                    }
                    else {
                        future.completeExceptionally(e);
                    }
                }
                if (!future.isDone()) {
                    future.complete(result);
                }
            });
            return null;
        });
        return new SyncFuture<>(mTaskQueue, handle.thenCompose(t -> future));
    }

    @Override
    public <U> SyncFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.handleAsync(fn));
    }

    @Override
    public <U> SyncFuture<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return new SyncFuture<>(mTaskQueue, mInnerFuture.handleAsync(fn, executor));
    }

    @Override
    public CompletableFuture<T> toCompletableFuture() {
        return mInnerFuture;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return mInnerFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return mInnerFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return mInnerFuture.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return mInnerFuture.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return mInnerFuture.get(timeout, unit);
    }
}
