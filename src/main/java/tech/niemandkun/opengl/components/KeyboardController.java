package tech.niemandkun.opengl.components;

import tech.niemandkun.opengl.engine.Component;
import tech.niemandkun.opengl.io.Keyboard;
import tech.niemandkun.opengl.io.KeyboardEvent;
import tech.niemandkun.opengl.math.Transform;

public abstract class KeyboardController extends Component implements Keyboard.KeyPressListener {
    @Override
    protected void onCreate() {
        super.onCreate();
        getScene().getKeyboard().addKeyPressListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getScene().getKeyboard().removeKeyPressListener(this);
    }

    public void onKeyPressed(Keyboard keyboard, KeyboardEvent event) {
        onKeyPressed(getActor().getTransform(), keyboard, event);
    }

    protected abstract void onKeyPressed(Transform actorTransform, Keyboard keyboard, KeyboardEvent event);
}
