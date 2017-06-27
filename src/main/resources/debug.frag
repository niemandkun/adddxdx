#version 330 core

layout(location = 0) out vec4 color;

uniform sampler2D mtexture;

in vec2 UV;

void main(){
    color = vec4(texture(mtexture, UV).x);
}
