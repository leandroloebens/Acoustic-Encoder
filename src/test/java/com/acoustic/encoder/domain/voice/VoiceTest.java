package com.acoustic.encoder.domain.voice;

import com.acoustic.encoder.domain.music.MusicalCommand;
import com.acoustic.encoder.domain.music.MusicalInstruction;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoiceTest {

    private VoiceConfig validConfig;
    private List<MusicalInstruction> validInstructions;

    @BeforeEach
    void setUp() {
        validConfig = new VoiceConfig(
                new InstrumentId(0),
                new Octave(5),
                new Volume(64)
        );

        validInstructions = List.of(
                new MusicalInstruction(MusicalCommand.PLAY_NOTE, 60),
                new MusicalInstruction(MusicalCommand.DELAY_BEATS, 1)
        );
    }

    @Test
    void shouldCreateVoiceSuccessfully() {
        assertDoesNotThrow(() -> new Voice(validInstructions, validConfig));
    }

    @Test
    void shouldThrowExceptionWhenMusicalInstructionsAreNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Voice(null, validConfig)
        );
        assertEquals("Musical instructions cannot be null!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenVoiceConfigIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new Voice(validInstructions, null)
        );
        assertEquals("VoiceConfig cannot be null!", exception.getMessage());
    }
}