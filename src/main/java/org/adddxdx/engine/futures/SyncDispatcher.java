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

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class SyncDispatcher implements TaskQueueDispatcher {

    private final Queue<Runnable> mTasks = new ConcurrentLinkedQueue<>();

    public int dispatch(Duration timeSinceLastTick, long totalTicksCount) {
        // TODO: Time measure
        int executed = 0;
        while (!mTasks.isEmpty()) {
            Runnable task = mTasks.poll();
            if (task != null) {
                executed++;
                task.run();
            }
        }
        return executed;
    }

    public void enqueue(Runnable command) {
        mTasks.add(command);
    }

    public boolean isNotEmpty() {
        return !mTasks.isEmpty();
    }
}
