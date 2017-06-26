package tech.niemandkun.opengl.engine;

public interface ActiveSystem<TComponent extends Component> extends System<TComponent> {
    void update();
}
