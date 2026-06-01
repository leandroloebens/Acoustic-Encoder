package com.acoustic.encoder.domain.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstrumentIdTest {

    @Test
    void shouldCreateInstrumentIdWithValidValue() {
        assertDoesNotThrow(() -> new InstrumentId(0));
    }

    @Test
    void shouldThrowExceptionWhenValueIsBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new InstrumentId(InstrumentId.MIN_INSTRUMENT_ID - 1)
        );
        assertTrue(exception.getMessage().contains("greater than " + InstrumentId.MIN_INSTRUMENT_ID));
    }

    @Test
    void shouldThrowExceptionWhenValueIsAboveMaximum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new InstrumentId(InstrumentId.MAX_INSTRUMENT_ID + 1)
        );
        assertTrue(exception.getMessage().contains("less than " + InstrumentId.MAX_INSTRUMENT_ID));
    }

    @Test
    void shouldReturnStringRepresentationOfValue() {
        InstrumentId instrumentId = new InstrumentId(42);
        assertEquals("42", instrumentId.toString());
    }
}