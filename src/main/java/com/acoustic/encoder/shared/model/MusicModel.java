package com.acoustic.encoder.shared.model;

public record MusicModel(
        VoiceList voices,
        int initialBpm
) {

    @Override
    public String toString() {
        return "MusicModel{" +
                "\nvoices=" + voices +
                "\ninitialBpm" + initialBpm +
                "\n}";
    }
}
