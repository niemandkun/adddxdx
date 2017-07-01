#version 330 core

layout(location = 0) out vec4 color;

uniform sampler2D mtexture;
in vec2 textureCoordinates;

void main() {
    color = texture(mtexture, textureCoordinates);
}
