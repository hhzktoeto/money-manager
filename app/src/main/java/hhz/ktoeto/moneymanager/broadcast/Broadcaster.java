package hhz.ktoeto.moneymanager.broadcast;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Component
public class Broadcaster {
    private final Map<Class<? extends BroadcastEvent>, List<Consumer<? extends BroadcastEvent>>> listeners = new ConcurrentHashMap<>();

    public <T extends BroadcastEvent> void register(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public <T extends BroadcastEvent> void unregister(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<? extends BroadcastEvent>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends BroadcastEvent> void broadcast(T event) {
        List<Consumer<? extends BroadcastEvent>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (Consumer<? extends BroadcastEvent> listener : eventListeners) {
                ((Consumer<T>) listener).accept(event);
            }
        }
    }
}
