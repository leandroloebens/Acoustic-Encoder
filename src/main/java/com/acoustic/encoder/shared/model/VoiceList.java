package com.acoustic.encoder.shared.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class VoiceList {

    private static final int MAX_VOICES = 15;

    private final List<Voice> voices = new ArrayList<>();

    public void add(Voice voice) {
        if (voices.size() >= MAX_VOICES) {
            throw new IllegalStateException("Maximum number of voices reached!");
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
