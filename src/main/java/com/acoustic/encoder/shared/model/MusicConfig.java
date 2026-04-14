package com.acoustic.encoder.shared.model;

public record MusicConfig(
        int defaultMidiInstrument,
        int bpm,
        int defaultOctave,
        int defaultVolume
) {

    @Override
    public String toString() {
        return "MusicConfig{" +
                "\ndefaultMidiInstrument=" + defaultMidiInstrument +
                ", \nbpm=" + bpm +
                ", \ndefaultOctave=" + defaultOctave +
                ", \ndefaultVolume=" + defaultVolume +
                "\n}";
    }
}
