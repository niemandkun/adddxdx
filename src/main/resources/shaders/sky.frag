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

#version 330 core

out vec4 color;

in vec3 cameraDirection_viewspace;

void main() {
    float green = 0.8 - clamp(cameraDirection_viewspace.y / 4, 0, 0.8);
    float red = 0.6 - clamp(cameraDirection_viewspace.y / 4, 0, 0.6);
    color = vec4(red, green, 0.8, 1);
}
