package com.acoustic.encoder.shared.model;

public record VoiceConfig(
        int defaultMidiInstrument,
        int defaultOctave,
        int defaultVolume
) {

    @Override
    public String toString() {
        return "VoiceConfig{" +
                "\ndefaultMidiInstrument=" + defaultMidiInstrument +
                ", \ndefaultOctave=" + defaultOctave +
                ", \ndefaultVolume=" + defaultVolume +
                "\n}";
    }
}
