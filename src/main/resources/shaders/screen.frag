#version 330 core

layout(location = 0) out vec4 color;

uniform sampler2D screenTexture;
in vec2 textureCoordinates;

void main() {
    color = texture(screenTexture, textureCoordinates);
}
