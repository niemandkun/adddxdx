package tech.niemandkun.opengl.engine;

interface System<TComponent extends Component> {
    void register(TComponent component);
    void unregister(TComponent component);
}
