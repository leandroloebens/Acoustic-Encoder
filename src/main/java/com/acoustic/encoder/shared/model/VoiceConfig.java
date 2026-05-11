package com.acoustic.encoder.shared.model;

public record VoiceConfig(
        int defaultMidiInstrument,
        int defaultBpm,
        int defaultOctave,
        int defaultVolume
) {

    @Override
    public String toString() {
        return "VoiceConfig{" +
                "\ndefaultMidiInstrument=" + defaultMidiInstrument +
                ", \ndefaultBpm=" + defaultBpm +
                ", \ndefaultOctave=" + defaultOctave +
                ", \ndefaultVolume=" + defaultVolume +
                "\n}";
    }
}
