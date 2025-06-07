package common.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private final List<EventHandler> handlers = new CopyOnWriteArrayList<>();
    private static final EventBus INSTANCE = new EventBus();

    private EventBus() {
    }

    public void register(EventHandler handler) {
        handlers.add(handler);
    }

    public void unregister(EventHandler handler) {
        handlers.remove(handler);
    }

    public void publish(Event event) {
        for (EventHandler handler : handlers) {
            handler.handle(event);
        }
    }

    public interface EventHandler {
        void handle(Event event);
    }

    public static EventBus getInstance() {
        return INSTANCE;
    }
}
