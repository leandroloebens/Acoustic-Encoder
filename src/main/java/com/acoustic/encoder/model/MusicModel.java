package com.acoustic.encoder.model;

import java.util.List;

public record MusicModel(
        List<MusicalInstruction> musicalInstructions,
        MusicConfig config
) {
    @Override
    public String toString() {
        return "MusicModel{" +
                "\nmusicalInstructions=" + musicalInstructions +
                "\n" + this.config.toString() +
                "\n}";
    }
}
