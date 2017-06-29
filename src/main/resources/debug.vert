#version 330 core

layout(location = 0) in vec3 vertexPosition;

out vec2 textureCoordinates;

void main(){
    gl_Position = vec4(vertexPosition,1);
    textureCoordinates = (vertexPosition.xy + vec2(1, 1)) / 2.0;
}
