package tech.niemandkun.opengl.engine;

class SystemInfo {
    private final Class<?> mComponentClass;
    private final System mSystem;

    SystemInfo(Class componentClass, System system) {
        mComponentClass = componentClass;
        mSystem = system;
    }

    boolean isAcceptingComponent(Class<? extends Component> componentClass) {
        return mComponentClass.isAssignableFrom(componentClass);
    }

    System getSystem() {
        return mSystem;
    }
}
