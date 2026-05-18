package com.acoustic.encoder.domain.voice;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;

public record VoiceConfig(
        InstrumentId defaultInstrument,
        Octave defaultOctave,
        Volume defaultVolume
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
