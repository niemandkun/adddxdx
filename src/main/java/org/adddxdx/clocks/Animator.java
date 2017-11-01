/*
 * Copyright (C) 2017 Poroshin Ivan
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

package org.adddxdx.clocks;

import org.adddxdx.math.Vector2;
import org.adddxdx.math.Vector3;
import org.adddxdx.math.Vector4;
import org.adddxdx.math.interpolators.Interpolator;
import org.adddxdx.math.interpolators.Interpolators;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Animator<T> extends TicksListener {

    public static <T> Builder<T>.InterpolatorSyntax of(Consumer<T> consumer) {
        return new Builder<>(consumer).new InterpolatorSyntax();
    }

    public static Builder<Float>.FromSyntax ofFloat(Consumer<Float> consumer) {
        return new Builder<>(consumer).new InterpolatorSyntax().using(Interpolators.linear());
    }

    public static Builder<Vector2>.FromSyntax ofVector2(Consumer<Vector2> consumer) {
        return new Builder<>(consumer).new InterpolatorSyntax().using(Interpolators.linear2());
    }

    public static Builder<Vector3>.FromSyntax ofVector3(Consumer<Vector3> consumer) {
        return new Builder<>(consumer).new InterpolatorSyntax().using(Interpolators.linear3());
    }

    public static Builder<Vector4>.FromSyntax ofVector4(Consumer<Vector4> consumer) {
        return new Builder<>(consumer).new InterpolatorSyntax().using(Interpolators.linear4());
    }

    private final List<AnimatorState<T>> mStateList;
    private final Interpolator<T> mInterpolator;
    private final Consumer<T> mConsumer;
    private final boolean mIsRepeated;

    private boolean mIsEnded;
    private int mCurrentState;
    private Duration mCurrentStateDuration;

    private Animator(boolean isRepeated, Interpolator<T> interpolator, Consumer<T> consumer, List<AnimatorState<T>> stateList) {
        mInterpolator = interpolator;
        mStateList = stateList;
        mConsumer = consumer;
        mIsRepeated = isRepeated;
        reset();
    }

    public void reset() {
        mCurrentState = 0;
        mCurrentStateDuration = Duration.ZERO;
        mIsEnded = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mConsumer.accept(getCurrentState().mValue);
    }

    @Override
    void onTick(Duration timeSinceLastTick, long totalTicksCount) {
        if (mIsEnded) {
            throw new IllegalStateException("Cannot run animation that is already ended.");
        }
        moveToNextState(timeSinceLastTick);
        if (!mIsRepeated && mCurrentState == mStateList.size() - 1) {
            System.out.println(getCurrentState().mValue);
            mConsumer.accept(getCurrentState().mValue);
            mIsEnded = true;
            getActor().removeComponent(this);
        } else {
            mConsumer.accept(getInterpolatedValue());
        }
    }

    private void moveToNextState(Duration timeSinceLastTick) {
        mCurrentStateDuration = mCurrentStateDuration.plus(timeSinceLastTick);
        while (mCurrentStateDuration.compareTo(getCurrentState().mTransitionDuration) >= 0
                && (mIsRepeated || mCurrentState < mStateList.size() - 1)) {
            mCurrentStateDuration = mCurrentStateDuration.minus(getCurrentState().mTransitionDuration);
            mCurrentState++;
        }
    }

    private AnimatorState<T> getCurrentState() {
        return getStateByIndex(mCurrentState);
    }

    private AnimatorState<T> getNextState() {
        return getStateByIndex(mCurrentState + 1);
    }

    private AnimatorState<T> getStateByIndex(int index) {
        return mStateList.get(index % mStateList.size());
    }

    private T getInterpolatedValue() {
        AnimatorState<T> currentState = getCurrentState();
        AnimatorState<T> nextState = getNextState();
        float proportion = (float) mCurrentStateDuration.toMillis() / currentState.mTransitionDuration.toMillis();
        return mInterpolator.interpolate(currentState.mValue, nextState.mValue, proportion);
    }

    private static class AnimatorState<T> {
        final Duration mTransitionDuration;
        final T mValue;

        private AnimatorState(T value, Duration transitionDuration) {
            mTransitionDuration = transitionDuration;
            mValue = value;
        }
    }

    public static class Builder<T> {
        private Consumer<T> mConsumer;
        private List<AnimatorState<T>> mAnimatorStates = new ArrayList<>();
        private T mFrom;
        private T mTo;
        private Interpolator<T> mInterpolator;
        private boolean mRepeated;

        private Builder(Consumer<T> consumer) {
            mConsumer = consumer;
        }

        private Animator<T> build() {
            if (mInterpolator == null) {
                throw new IllegalStateException("Should specify interpolator for custom types.");
            }
            if (mAnimatorStates.size() < 2) {
                throw new IllegalStateException("Should specify at least two animator stated.");
            }
            if (mConsumer == null) {
                throw new IllegalStateException("Should specify target for animator.");
            }
            return new Animator<>(mRepeated, mInterpolator, mConsumer, mAnimatorStates);
        }

        public class InterpolatorSyntax {
            private InterpolatorSyntax() { }

            public FromSyntax using(Interpolator<T> interpolator) {
                mInterpolator = interpolator;
                return new FromSyntax();
            }
        }

        public class FromSyntax {
            private FromSyntax() { }

            public ToSyntax from(T value) {
                mFrom = value;
                return new ToSyntax();
            }

            public FromSyntax using(Interpolator<T> interpolator) {
                mInterpolator = interpolator;
                return this;
            }
        }

        public class ToSyntax {
            private ToSyntax() { }

            public DurationSyntax to(T value) {
                mTo = value;
                return new DurationSyntax();
            }
        }

        public class DurationSyntax {
            private DurationSyntax() { }

            public BranchSyntax immediately() {
                return in(Duration.ZERO);
            }

            public BranchSyntax in(Duration duration) {
                mAnimatorStates.add(new AnimatorState<>(mFrom, duration));
                mFrom = mTo;
                return new BranchSyntax();
            }
        }

        public class BranchSyntax {
            private BranchSyntax() { }

            public Animator<T> build() {
                mAnimatorStates.add(new AnimatorState<>(mTo, Duration.ZERO));
                return Builder.this.build();
            }

            public DurationSyntax to(T value) {
                mTo = value;
                return new DurationSyntax();
            }

            public BuildSyntax repeat() {
                return repeatIn(Duration.ZERO);
            }

            public BuildSyntax repeatIn(Duration duration) {
                mRepeated = true;
                mAnimatorStates.add(new AnimatorState<>(mTo, duration));
                return new BuildSyntax();
            }
        }

        public class BuildSyntax {
            private BuildSyntax() { }

            public Animator<T> build() {
                return Builder.this.build();
            }
        }
    }
}
