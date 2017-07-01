#version 330 core

layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec3 vertexNormal_modelspace;
layout(location = 2) in vec2 vertexUvPosition;

uniform mat4 mvpMatrix;
uniform mat4 mvMatrix;
uniform mat4 vMatrix;

uniform mat4 lightMatrix;
uniform vec3 lightDirection;

out vec3 shadowMapPosition;
out vec2 fragmentUvPosition;
out vec3 fragmentNormal_viewspace;
out vec3 lightDirection_viewspace;
out vec3 cameraDirection_viewspace;

void main() {
    vec4 vertexPosition_vec4 = vec4(vertexPosition_modelspace, 1);
    vec4 vertexNormal_vec4 = vec4(vertexNormal_modelspace, 0);
    vec4 lightDirection_vec4 = vec4(lightDirection, 0);

    gl_Position = mvpMatrix * vertexPosition_vec4;

    shadowMapPosition = (lightMatrix * vertexPosition_vec4).xyz;

    fragmentUvPosition = vertexUvPosition;

    fragmentNormal_viewspace = (mvMatrix * vertexNormal_vec4).xyz;

    lightDirection_viewspace = (vMatrix * lightDirection_vec4).xyz;

    cameraDirection_viewspace = -(mvMatrix * vertexPosition_vec4).xyz;
}
