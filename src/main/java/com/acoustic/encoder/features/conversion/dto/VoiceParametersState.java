package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.Objects;

public class VoiceParametersState {

    private InstrumentId instrument;
    private Volume volume;
    private Octave octave;

    public VoiceParametersState(Volume volume, Octave octave, InstrumentId instrument) {
        this.volume = Objects.requireNonNull(volume, "volume cannot be null");
        this.octave = Objects.requireNonNull(octave, "octave cannot be null");
        this.instrument = Objects.requireNonNull(instrument, "instrument cannot be null");
    }

    public VoiceParametersState(VoiceConfig config) {
        Objects.requireNonNull(config, "config cannot be null");

        this.volume = config.defaultVolume();
        this.octave = config.defaultOctave();
        this.instrument = config.defaultInstrument();
    }

    public InstrumentId getInstrument() {
        return instrument;
    }

    public void setInstrument(InstrumentId instrument) {
        this.instrument = Objects.requireNonNull(instrument, "instrument cannot be null");
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = Objects.requireNonNull(volume, "volume cannot be null");
    }

    public Octave getOctave() {
        return octave;
    }

    public void setOctave(Octave octave) {
        this.octave = Objects.requireNonNull(octave, "octave cannot be null");
    }

    @Override
    public String toString() {
        return instrument + ", " + volume + ", " + octave;
    }
}
