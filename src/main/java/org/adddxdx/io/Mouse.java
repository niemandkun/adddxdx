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

package org.adddxdx.io;

import org.adddxdx.math.Vector2;

public interface Mouse {
    interface ScrollListener {
        void onWheelScrolled(Mouse mouse, MouseEvent event);
    }

    void addScrollListener(ScrollListener listener);
    void removeScrollListener(ScrollListener listener);

    interface ButtonPressListener {
        void onButtonPressed(Mouse mouse, MouseEvent event);
    }

    void addButtonPressListener(ButtonPressListener listener);
    void removeButtonPressListener(ButtonPressListener listener);

    interface ButtonReleaseListener {
        void onButtonReleased(Mouse mouse, MouseEvent event);
    }

    void addButtonReleaseListener(ButtonReleaseListener listener);
    void removeButtonReleaseListener(ButtonReleaseListener listener);

    interface MovementListener {
        void onPointerMoved(Mouse mouse, MouseEvent event);
    }

    void addMovementListener(MovementListener listener);
    void removeMovementListener(MovementListener listener);

    boolean isButtonPressed(int button);
    Vector2 getPointerPosition();
}
