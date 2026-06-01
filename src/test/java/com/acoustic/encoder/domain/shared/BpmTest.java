package com.acoustic.encoder.domain.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BpmTest {

    @Test
    void shouldCreateBpmWithValidValueInsideBounds() {
        assertDoesNotThrow(() -> new Bpm(120));
    }

    @Test
    void shouldCreateBpmWithMinimumValidValue() {
        assertDoesNotThrow(() -> new Bpm(Bpm.MIN_BPM));
    }

    @Test
    void shouldCreateBpmWithMaximumValidValue() {
        assertDoesNotThrow(() -> new Bpm(Bpm.MAX_BPM));
    }

    @Test
    void shouldThrowExceptionWhenValueIsBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Bpm(Bpm.MIN_BPM - 1)
        );
        assertTrue(exception.getMessage().contains("greater than " + Bpm.MIN_BPM));
    }

    @Test
    void shouldThrowExceptionWhenValueIsAboveMaximum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Bpm(Bpm.MAX_BPM + 1)
        );
        assertTrue(exception.getMessage().contains("less than " + Bpm.MAX_BPM));
    }
}