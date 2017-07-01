#version 330 core

layout(location = 0) in vec3 vertexPosition_modelspace;

out vec2 textureCoordinates;

void main() {
    gl_Position = vec4(vertexPosition_modelspace, 1.0);
    textureCoordinates = (vertexPosition_modelspace.xy - vec2(1)) / 2;
}
