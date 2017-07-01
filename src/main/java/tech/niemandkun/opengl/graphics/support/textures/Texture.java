package tech.niemandkun.opengl.graphics.support.textures;

import tech.niemandkun.opengl.fio.Image;
import tech.niemandkun.opengl.graphics.GlTexture;
import tech.niemandkun.opengl.math.Size;

import static org.lwjgl.opengl.GL11.*;

public class Texture extends GlTexture {
    private final Image mImage;

    public Texture(Image image) {
        super(image.getSize());
        mImage = image;
    }

    @Override
    protected int getGlTextureFormat() {
        return mImage.getChannelsCount() == 3 ? GL_RGB : GL_RGBA;
    }

    @Override
    protected int doInit(Size size) {
        int textureHandle = glGenTextures();
        int glTextureFormat = getGlTextureFormat();

        glBindTexture(GL_TEXTURE_2D, textureHandle);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0,
                glTextureFormat, mImage.getSize().getWidth(), mImage.getSize().getHeight(), 0,
                glTextureFormat, GL_UNSIGNED_BYTE, mImage.getImageBytes());

        return textureHandle;
    }
}
