package com.acoustic.encoder.domain.music;

import java.util.Objects;

public record InstrumentOption(String name, int id) {

    public InstrumentOption {
        Objects.requireNonNull(name, "name must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
