#version 330 core

layout(location = 0) in vec4 vertexPosition;
layout(location = 1) in vec3 vertexNormal;
layout(location = 2) in vec3 vertexColor;
layout(location = 3) in vec3 vertexUvPosition;

uniform mat4 mvp;
uniform mat4 lightMvp;
uniform vec4 lightDirection;

out vec3 fragmentColor;
out vec4 shadowMapPosition;

void main() {
    gl_Position = mvp * vertexPosition;
    shadowMapPosition = lightMvp * vertexPosition;
    fragmentColor = vertexColor;
}
