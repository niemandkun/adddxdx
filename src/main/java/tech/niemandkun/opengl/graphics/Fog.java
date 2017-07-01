package tech.niemandkun.opengl.graphics;

import tech.niemandkun.opengl.math.Color;

public interface Fog {
    Color getColor();
    float getExtinction();
    float getDensity();
}
