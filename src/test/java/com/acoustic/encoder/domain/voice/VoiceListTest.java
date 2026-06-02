package com.acoustic.encoder.domain.voice;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoiceListTest {

    private VoiceList voiceList;

    @BeforeEach
    void setUp() {
        voiceList = new VoiceList();
    }

    // Helper method to generate real valid instances for testing
    private Voice createDummyVoice() {
        VoiceConfig config = new VoiceConfig(
                new InstrumentId(10),
                new Octave(4),
                new Volume(100)
        );
        return new Voice(List.of(), config);
    }

    @Test
    void shouldAddVoiceSuccessfully() {
        Voice voice = createDummyVoice();
        voiceList.add(voice);

        assertEquals(1, voiceList.getVoices().size());
        assertEquals(voice, voiceList.getVoices().get(0));
    }

    @Test
    void shouldThrowExceptionWhenAddingNullVoice() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> voiceList.add(null)
        );
        assertEquals("Voice cannot be null!", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAddingMoreThanMaxVoices() {
        // Arrange: Fill the list up to the maximum limit (15)
        for (int i = 0; i < 15; i++) {
            voiceList.add(createDummyVoice());
        }

        // Act & Assert: Attempt to add the 16th voice
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> voiceList.add(createDummyVoice())
        );
        assertTrue(exception.getMessage().contains("Maximum number of voices reached: 15"));
    }

    @Test
    void shouldReturnUnmodifiableList() {
        Voice voice = createDummyVoice();
        voiceList.add(voice);

        List<Voice> returnedList = voiceList.getVoices();

        // Assert that the returned list cannot be modified (encapsulation check)
        assertThrows(UnsupportedOperationException.class, () -> returnedList.add(createDummyVoice()));
    }
}