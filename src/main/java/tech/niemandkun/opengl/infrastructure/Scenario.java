package tech.niemandkun.opengl.infrastructure;

import java.util.Stack;

class Scenario {
    private final Stack<Scene> mSceneStack;
    private final Setting mSetting;

    Scenario(Setting setting) {
        mSceneStack = new Stack<>();
        mSetting = setting;
    }

    private Scene createScene(Class<? extends Scene> sceneClass) {
        Scene instance = instantiateScene(sceneClass);
        instance.setSetting(mSetting);
        return instance;
    }

    private Scene instantiateScene(Class<? extends Scene> sceneClass) {
        try {
            return sceneClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate scene, " + e.getMessage());
        }
    }

    Scene peekScene() {
        return mSceneStack.peek();
    }

    void popAndPush(Class<? extends Scene> sceneClass) {
        if (!mSceneStack.isEmpty()) {
            Scene toPop = mSceneStack.pop();
            toPop.onPause();
            toPop.onDestroy();
        }

        Scene toPush = createScene(sceneClass);
        toPush.onCreate();
        toPush.onResume();

        mSceneStack.push(toPush);
    }

    void push(Class<? extends Scene> sceneClass) {
        if (!mSceneStack.isEmpty())
            mSceneStack.peek().onPause();

        Scene toPush = createScene(sceneClass);
        toPush.onCreate();
        toPush.onResume();

        mSceneStack.push(toPush);
    }

    void popScene() {
        Scene toPop = mSceneStack.pop();
        toPop.onPause();
        toPop.onDestroy();

        if (!mSceneStack.isEmpty())
            mSceneStack.peek().onResume();
    }
}
