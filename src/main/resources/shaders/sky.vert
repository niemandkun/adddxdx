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

layout(location = 0) in vec3 vertexPosition;

uniform mat4 inverseProjectionViewMatrix;

out vec3 cameraDirection_viewspace;

void main() {
    gl_Position = vec4(vertexPosition.xy, 0.999, 1);
    cameraDirection_viewspace = (inverseProjectionViewMatrix * gl_Position).xyz;
}
