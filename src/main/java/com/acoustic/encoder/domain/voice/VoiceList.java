package com.acoustic.encoder.domain.voice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class VoiceList {

    private static final int MAX_VOICES = 15;

    private final List<Voice> voices = new ArrayList<>();

    public void add(Voice voice) {
        Objects.requireNonNull(voice, "Voice cannot be null!");

        if (voices.size() >= MAX_VOICES) {
            throw new IllegalStateException("Maximum number of voices reached: " + MAX_VOICES);
        }

        voices.add(voice);
    }

    public List<Voice> getVoices() {
        return Collections.unmodifiableList(voices);
    }

    @Override
    public String toString() {
        return "VoiceList{" +
                "\n" + voices +
                "\n}";
    }
}
