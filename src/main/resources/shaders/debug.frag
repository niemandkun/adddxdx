#version 330 core

layout(location = 0) out vec4 color;

uniform sampler2D shadowMap;

in vec2 textureCoordinates;

void main(){
    color = vec4(texture(shadowMap, textureCoordinates).x);
}
