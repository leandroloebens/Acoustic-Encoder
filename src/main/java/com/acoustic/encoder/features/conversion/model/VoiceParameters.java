package com.acoustic.encoder.features.conversion.model;

public class VoiceParameters {

    private int instrument;
    private int volume;
    private int octave;

    public VoiceParameters(int volume, int octave, int instrument) {
        this.volume = volume;
        this.octave = octave;
        this.instrument = instrument;
    }

    public VoiceParameters(VoiceParameters other) {
        this.volume = other.volume;
        this.octave = other.octave;
        this.instrument = other.instrument;
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
