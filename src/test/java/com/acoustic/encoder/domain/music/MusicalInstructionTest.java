package com.acoustic.encoder.domain.music;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MusicalInstructionTest {

    @Test
    void shouldCreateMusicalInstructionSuccessfully() {
        assertDoesNotThrow(() -> new MusicalInstruction(MusicalCommand.PLAY_NOTE, 10));
    }

    @Test
    void shouldCreateInstructionWithNullCommandSuccessfully() {
        // NULL_COMMAND is a valid enum value representing the absence of action
        assertDoesNotThrow(() -> new MusicalInstruction(MusicalCommand.NULL_COMMAND, 0));
    }

    @Test
    void shouldThrowExceptionWhenCommandIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new MusicalInstruction(null, 10)
        );
        assertEquals("Command cannot be null!", exception.getMessage());
    }
}