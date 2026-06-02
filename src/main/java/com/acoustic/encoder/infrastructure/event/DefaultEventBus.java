package com.acoustic.encoder.infrastructure.event;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.infrastructure.ui_shared.UiThreadDispatcher;

import java.util.*;

public class DefaultEventBus implements EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    private final Map<Class<?>, Map<EventListener<?>, EventListener<?>>> wrapperMap = new HashMap<>();

    private final UiThreadDispatcher uiThreadDispatcher;

    public DefaultEventBus(UiThreadDispatcher uiThreadDispatcher) {
        this.uiThreadDispatcher =
                Objects.requireNonNull(uiThreadDispatcher, "UiThreadDispatcher cannot be null");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {
        List<EventListener<?>> snapshot;

        synchronized (this) {
            List<EventListener<?>> eventListeners = this.listeners.get(event.getClass());
            if (eventListeners == null || eventListeners.isEmpty()) {
                return;
            }
            snapshot = new ArrayList<>(eventListeners);
        }

        for (EventListener<?> listener : snapshot) {
            ((EventListener<T>) listener).onEvent(event);
        }
    }

    @Override
    public synchronized <T> void subscribe(Class<T> eventType, EventListener<T> listener) {
        this.listeners
                .computeIfAbsent(eventType, k -> new ArrayList<>())
                .add(listener);
    }

    @Override
    public synchronized <T> void subscribeOnUiThread(Class<T> eventType, EventListener<T> listener) {
        EventListener<T> wrappedListener = event ->
                uiThreadDispatcher.dispatchOnUiThread(() -> listener.onEvent(event));

        wrapperMap
                .computeIfAbsent(eventType, k -> new IdentityHashMap<>())
                .put(listener, wrappedListener);

        subscribe(eventType, wrappedListener);
    }

    @Override
    public synchronized <T> void unsubscribe(Class<T> eventType, EventListener<T> listener) {
        Map<EventListener<?>, EventListener<?>> mapForType = wrapperMap.get(eventType);
        EventListener<?> toRemove = null;

        if (mapForType != null) {
            if (mapForType.containsKey(listener)) {
                toRemove = mapForType.remove(listener);
            } else {
                EventListener<?> foundOriginal = null;
                for (Map.Entry<EventListener<?>, EventListener<?>> e : mapForType.entrySet()) {
                    if (e.getValue() == listener) {
                        foundOriginal = e.getKey();
                        break;
                    }
                }
                if (foundOriginal != null) {
                    toRemove = mapForType.remove(foundOriginal);
                }
            }

            if (mapForType.isEmpty()) {
                wrapperMap.remove(eventType);
            }
        }

        EventListener<?> actual = (toRemove != null) ? toRemove : listener;

        this.listeners.computeIfPresent(eventType, (k, v) -> {
            v.remove(actual);
            return v.isEmpty() ? null : v;
        });
    }
}