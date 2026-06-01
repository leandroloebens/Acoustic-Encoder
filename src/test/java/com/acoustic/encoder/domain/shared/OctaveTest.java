package com.acoustic.encoder.domain.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OctaveTest {

    @Test
    void shouldCreateOctaveWithValidValue() {
        assertDoesNotThrow(() -> new Octave(5));
    }

    @Test
    void shouldThrowExceptionWhenValueIsBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Octave(Octave.MIN_OCTAVE - 1)
        );
        assertTrue(exception.getMessage().contains("greater than " + Octave.MIN_OCTAVE));
    }

    @Test
    void shouldThrowExceptionWhenValueIsAboveMaximum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Octave(Octave.MAX_OCTAVE + 1)
        );
        assertTrue(exception.getMessage().contains("less than " + Octave.MAX_OCTAVE));
    }
}