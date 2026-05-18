package com.acoustic.encoder.domain.music;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.voice.VoiceList;

import java.util.Objects;

public record MusicModel(
        VoiceList voices,
        Bpm initialBpm
) {

    public MusicModel {
        Objects.requireNonNull(voices, "voices cannot be null");
        Objects.requireNonNull(initialBpm, "initialBpm cannot be null");
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "\nvoices=" + voices +
                "\ninitialBpm" + initialBpm +
                "\n}";
    }
}
