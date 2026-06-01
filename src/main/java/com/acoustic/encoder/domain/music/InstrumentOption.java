package com.acoustic.encoder.domain.music;

import com.acoustic.encoder.domain.shared.InstrumentId;

import java.util.Objects;

public record InstrumentOption(String name, InstrumentId instrumentId) {

    public InstrumentOption {
        Objects.requireNonNull(name, "name must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        Objects.requireNonNull(instrumentId, "instrumentId must not be null");
    }

    @Override
    public String toString() {
        return instrumentId + " - " + name;
    }

}
