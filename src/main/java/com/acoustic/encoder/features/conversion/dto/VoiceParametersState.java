package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.domain.voice.VoiceConfig;

public class VoiceParametersState {

    private InstrumentId instrument;
    private Volume volume;
    private Octave octave;

    public VoiceParametersState(Volume volume, Octave octave, InstrumentId instrument) {
        this.volume = volume;
        this.octave = octave;
        this.instrument = instrument;
    }

    public VoiceParametersState(VoiceParametersState other) {
        this.volume = other.volume;
        this.octave = other.octave;
        this.instrument = other.instrument;
    }

    public VoiceParametersState(VoiceConfig config) {
        this.volume = config.defaultVolume();
        this.octave = config.defaultOctave();
        this.instrument = config.defaultInstrument();
    }

    public InstrumentId getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentId instrument) {
        this.instrument = instrument;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Octave getOctave() {
        return octave;
    }

    public void setOctave(Octave octave) {
        this.octave = octave;
    }

    @Override
    public String toString() {
        return "instrument=" + instrument + ", volume=" + volume + ", octave=" + octave;
    }
}
