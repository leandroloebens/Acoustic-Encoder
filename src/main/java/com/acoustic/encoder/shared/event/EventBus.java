package com.acoustic.encoder.shared.event;

public interface EventBus {

    <T> void subscribe(Class<T> eventType, EventListener<T> listener);

    <T> void publish(T event);

}
