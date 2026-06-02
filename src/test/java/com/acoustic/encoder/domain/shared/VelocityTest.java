package com.acoustic.encoder.domain.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VelocityTest {

    @Test
    void shouldCreateVelocityWithValidValueInsideBounds() {
        assertDoesNotThrow(() -> new Velocity(64));
    }

    @Test
    void shouldCreateVelocityWithMinimumValidValue() {
        assertDoesNotThrow(() -> new Velocity(Velocity.MIN_VELOCITY));
    }

    @Test
    void shouldCreateVelocityWithMaximumValidValue() {
        assertDoesNotThrow(() -> new Velocity(Velocity.MAX_VELOCITY));
    }

    @Test
    void shouldThrowExceptionWhenValueIsBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Velocity(Velocity.MIN_VELOCITY - 1)
        );
        assertTrue(exception.getMessage().contains("greater than " + Velocity.MIN_VELOCITY));
    }

    @Test
    void shouldThrowExceptionWhenValueIsAboveMaximum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Velocity(Velocity.MAX_VELOCITY + 1)
        );
        assertTrue(exception.getMessage().contains("less than " + Velocity.MAX_VELOCITY));
    }
}