package com.acoustic.encoder.domain.music;

import com.acoustic.encoder.domain.voice.VoiceList;

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
