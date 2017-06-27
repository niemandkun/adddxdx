package tech.niemandkun.opengl.engine;

import java.time.Duration;
import java.time.Instant;

public abstract class ActiveSystem<TComponent extends Component> implements System<TComponent> {
    private Instant lastUpdate = null;

    protected Duration getUpdateInterval() { return Duration.ZERO; }

    void fixedUpdate() {
        Instant now = Instant.now();
        if (lastUpdate == null) lastUpdate = now;

        Duration timeSinceLastUpdate = Duration.between(lastUpdate, now);

        if (timeSinceLastUpdate.compareTo(getUpdateInterval()) > 0) {
            update(timeSinceLastUpdate);
            java.lang.System.out.println(timeSinceLastUpdate);
            lastUpdate = now;
        }
    }

    public abstract void update(Duration timeSinceLastUpdate);
}
