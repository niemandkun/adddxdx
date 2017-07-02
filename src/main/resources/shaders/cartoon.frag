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

vec2 poissonDisk[] = vec2[] (
    vec2(-0.94201624, -0.39906216),
    vec2(0.94558609, -0.76890725),
    vec2(-0.094184101, -0.92938870),
    vec2(0.34495938, 0.29387760),
    vec2(-0.91588581, 0.45771432),
    vec2(-0.81544232, -0.87912464),
    vec2(-0.38277543, 0.27676845),
    vec2(0.97484398, 0.75648379),
    vec2(0.44323325, -0.97511554),
    vec2(0.53742981, -0.47373420),
    vec2(-0.26496911, -0.41893023),
    vec2(0.79197514, 0.19090188),
    vec2(-0.24188840, 0.99706507),
    vec2(-0.81409955, 0.91437590),
    vec2(0.19984126, 0.78641367),
    vec2(0.14383161, -0.14100790)
);

in vec3 shadowMapPosition;
in vec2 fragmentUvPosition;
in vec3 fragmentNormal_viewspace;
in vec3 lightDirection_viewspace;
in vec3 cameraDirection_viewspace;

layout(location = 0) out vec4 color;

uniform vec3 fogColor;
uniform float fogExtinction;
uniform float fogDensity;

uniform vec3 lightColor;
uniform float ambientLight;

uniform vec3 materialSpecularColor;
uniform vec3 materialColor;
uniform float materialShininess;

uniform sampler2D shadowMap;
uniform sampler2D mainTexture;

void main() {
    vec3 totalLightColor = (1 - ambientLight) * lightColor;

    vec4 textureSample = texture(mainTexture, fragmentUvPosition);
    vec3 diffuseColor = materialColor * textureSample.rgb;
    vec3 ambientColor = ambientLight * lightColor * diffuseColor;

    vec3 normal = normalize(fragmentNormal_viewspace);
    vec3 light = normalize(lightDirection_viewspace);
    float cosLightToNormal = clamp(-dot(normal, light), 0, 1);

    float bias = 0.005;
    float visibility = 1;

    vec3 shadowMapCoords = (shadowMapPosition.xyz + vec3(1)) / 2;

    for (int i = 0; i < 16; ++i) {
        if (texture(shadowMap, shadowMapCoords.xy + poissonDisk[i] / 1000).x < shadowMapCoords.z - bias)
            visibility -= 0.0625f;
    }

    vec3 camera = normalize(cameraDirection_viewspace);
    vec3 reflection = reflect(-light, normal);
    float cosCameraToReflection = clamp(-dot(camera, reflection), 0, 1);

    float litFactor = cosLightToNormal > 0 ? 1 : 0;
    float specularFactor = pow(cosCameraToReflection, materialShininess) > 0.5 ? 1 : 0;
    float diffuseFactor = cosLightToNormal > 0.5 || specularFactor == 1 ? 1 : 0.5;

    vec3 materialColor = ambientColor
        + litFactor * visibility * totalLightColor
        * (diffuseColor * diffuseFactor + materialSpecularColor * specularFactor);

    float distance = length(cameraDirection_viewspace);
    float fogAmount = fogExtinction * (1.0 - exp(-distance * fogDensity));
    color = vec4(mix(materialColor, fogColor, fogAmount).rgb, textureSample.a);
}
