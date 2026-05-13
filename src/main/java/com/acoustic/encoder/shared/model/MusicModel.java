package com.acoustic.encoder.shared.model;

public record MusicModel(
        VoiceList voices,
        int bpm
) {
    @Override
    public String toString() {
        return "MusicModel{" +
                "\nvoices=" + voices +
                "\nbpm" + bpm +
                "\n}";
    }
}
