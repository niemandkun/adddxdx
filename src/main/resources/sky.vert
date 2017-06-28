#version 330 core

layout(location = 0) in vec3 vertexPosition;

uniform mat4 inverseProjectionMatrix;

out vec3 cameraDirection_viewspace;

void main() {
    gl_Position = vec4(vertexPosition.xy, 0.999, 1);
    cameraDirection_viewspace = (inverseProjectionMatrix * gl_Position).xyz;
}
