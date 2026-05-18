package com.acoustic.encoder.domain.voice;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;

import java.util.Objects;

public record VoiceConfig(
        InstrumentId defaultInstrument,
        Octave defaultOctave,
        Volume defaultVolume
) {

    public VoiceConfig {
        Objects.requireNonNull(defaultInstrument, "defaultInstrument cannot be null");
        Objects.requireNonNull(defaultOctave, "defaultOctave cannot be null");
        Objects.requireNonNull(defaultVolume, "defaultVolume cannot be null");
    }

    @Override
    public String toString() {
        return "VoiceConfig{" +
                "\ndefaultInstrument=" + defaultInstrument +
                ", \ndefaultOctave=" + defaultOctave +
                ", \ndefaultVolume=" + defaultVolume +
                "\n}";
    }
}
