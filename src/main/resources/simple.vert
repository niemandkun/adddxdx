#version 330

layout(location = 0) in vec4 vertexPosition;
layout(location = 1) in vec3 vertexNormal;
layout(location = 2) in vec3 vertexColor;
layout(location = 3) in vec3 vertexUvPosition;

uniform mat4 mvp;

out vec3 fragmentColor;

void main() {
    gl_Position = mvp * vertexPosition;
    fragmentColor = vertexColor;
}
