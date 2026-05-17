package com.acoustic.encoder.domain.shared;

public record InstrumentId(int value) {

    private static final int MIN_INSTRUMENT_ID = 0;
    private static final int MAX_INSTRUMENT_ID = 127;

    public InstrumentId {
        if (value < MIN_INSTRUMENT_ID) {
            throw new IllegalArgumentException("Instrument ID value must be greater than " + MIN_INSTRUMENT_ID);
        }
        if (value > MAX_INSTRUMENT_ID) {
            throw new IllegalArgumentException("Instrument ID value must be less than " + MAX_INSTRUMENT_ID);
        }
    }
}
