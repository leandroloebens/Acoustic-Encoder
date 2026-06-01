package com.acoustic.encoder.domain.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VolumeTest {

    @Test
    void shouldCreateVolumeWithValidValue() {
        assertDoesNotThrow(() -> new Volume(64));
    }

    @Test
    void shouldThrowExceptionWhenValueIsBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Volume(Volume.MIN_VOLUME - 1)
        );
        assertTrue(exception.getMessage().contains("greater than " + Volume.MIN_VOLUME));
    }

    @Test
    void shouldThrowExceptionWhenValueIsAboveMaximum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Volume(Volume.MAX_VOLUME + 1)
        );
        assertTrue(exception.getMessage().contains("less than " + Volume.MAX_VOLUME));
    }
}