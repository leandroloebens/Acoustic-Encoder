package com.acoustic.encoder.shared.model;

public record VoiceConfig(
        int defaultInstrument,
        int defaultOctave,
        int defaultVolume
) {

    @Override
    public String toString() {
        return "VoiceConfig{" +
                "\ndefaultInstrument=" + defaultInstrument +
                ", \ndefaultOctave=" + defaultOctave +
                ", \ndefaultVolume=" + defaultVolume +
                "\n}";
    }
}
