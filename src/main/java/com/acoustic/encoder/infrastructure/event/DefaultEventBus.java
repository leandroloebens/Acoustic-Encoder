package com.acoustic.encoder.infrastructure.event;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.domain.event.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultEventBus implements EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {

        List<EventListener<?>> eventListeners = this.listeners
                .getOrDefault(event.getClass(), null);

        if (eventListeners != null) {
            for (EventListener<?> listener : eventListeners) {
                ((EventListener<T>) listener).onEvent(event);
            }
        }
    }

    public <T> void subscribe(Class<T> eventType, EventListener<T> listener) {
        this.listeners
                .computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(listener);
    }

    public <T> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
        this.listeners
                .computeIfPresent(eventType, (k, v) -> {
                    v.remove(listener);
                    return v;
                });
    }
}
