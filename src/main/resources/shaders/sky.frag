#version 330 core

out vec4 color;

in vec3 cameraDirection_viewspace;

void main() {
    float green = 0.8 - clamp(cameraDirection_viewspace.y / 4, 0, 0.8);
    float red = 0.6 - clamp(cameraDirection_viewspace.y / 4, 0, 0.6);
    color = vec4(red, green, 0.8, 1);
}
