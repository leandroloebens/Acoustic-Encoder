package com.acoustic.encoder.domain.voice;

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
