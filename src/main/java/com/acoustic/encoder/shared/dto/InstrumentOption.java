package com.acoustic.encoder.shared.dto;

public record InstrumentOption(String name, int id) {

    @Override
    public String toString() {
        return name;
    }

}
