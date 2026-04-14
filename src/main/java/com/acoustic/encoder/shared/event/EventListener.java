package com.acoustic.encoder.shared.event;

public interface EventListener<T> {

    void onEvent(T event);
}
