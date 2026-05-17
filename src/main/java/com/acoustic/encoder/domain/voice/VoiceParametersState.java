package com.acoustic.encoder.domain.voice;

public class VoiceParametersState {

    private int instrument;
    private int volume;
    private int octave;

    public VoiceParametersState(int volume, int octave, int instrument) {
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

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    @Override
    public String toString() {
        return "instrument=" + instrument + ", volume=" + volume + ", octave=" + octave;
    }
}
