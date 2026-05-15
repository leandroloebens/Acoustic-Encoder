package com.acoustic.encoder.domain.event;

public interface EventListener<T> {

    void onEvent(T event);
}
