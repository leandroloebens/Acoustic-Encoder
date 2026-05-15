package com.acoustic.encoder.domain.music;

public record InstrumentOption(String name, int id) {

    @Override
    public String toString() {
        return name;
    }

}
