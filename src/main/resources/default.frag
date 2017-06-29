#version 330 core

in vec3 shadowMapPosition;
in vec3 fragmentColor;
in vec3 fragmentNormal_viewspace;
in vec3 lightDirection_viewspace;
in vec3 cameraDirection_viewspace;

out vec3 color;

uniform sampler2D shadowMap;

vec2 poissonDisk[16] = vec2[](
   vec2( -0.94201624, -0.39906216 ),
   vec2( 0.94558609, -0.76890725 ),
   vec2( -0.094184101, -0.92938870 ),
   vec2( 0.34495938, 0.29387760 ),
   vec2( -0.91588581, 0.45771432 ),
   vec2( -0.81544232, -0.87912464 ),
   vec2( -0.38277543, 0.27676845 ),
   vec2( 0.97484398, 0.75648379 ),
   vec2( 0.44323325, -0.97511554 ),
   vec2( 0.53742981, -0.47373420 ),
   vec2( -0.26496911, -0.41893023 ),
   vec2( 0.79197514, 0.19090188 ),
   vec2( -0.24188840, 0.99706507 ),
   vec2( -0.81409955, 0.91437590 ),
   vec2( 0.19984126, 0.78641367 ),
   vec2( 0.14383161, -0.14100790 )
);

void main() {
    vec3 lightColor = vec3(0.75);
    vec3 ambientLight = vec3(0.25);

    float lightIntencity = 1.0f;
    float shininess = 5;

    vec3 materialDiffuseColor = fragmentColor;
    vec3 materialAmbientColor = ambientLight * materialDiffuseColor;
    vec3 materialSpecularColor = vec3(0.1);

    vec3 normal = normalize(fragmentNormal_viewspace);
    vec3 light = normalize(lightDirection_viewspace);
    float cosLightToNormal = clamp(-dot(normal, light), 0, 1);

    float bias = 0.005;
    float visibility = 1;

    vec3 shadowMapCoords = (shadowMapPosition.xyz + vec3(1)) / 2;

    for (int i = 0; i < 16; ++i) {
        if (texture(shadowMap, shadowMapCoords.xy + poissonDisk[i] / 800).x < shadowMapCoords.z - bias)
            visibility -= 0.0625f;
    }

    vec3 camera = normalize(cameraDirection_viewspace);
    vec3 reflection = reflect(-light, normal);
    float cosCameraToReflection = clamp(-dot(camera, reflection), 0, 1);

    vec3 diffuse = materialDiffuseColor * cosLightToNormal;
    vec3 specular = materialSpecularColor * pow(cosCameraToReflection, shininess);

    color = materialAmbientColor + visibility * lightColor * (diffuse + specular);
}
