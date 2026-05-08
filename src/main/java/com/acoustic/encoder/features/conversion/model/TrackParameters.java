package com.acoustic.encoder.features.conversion.model;

public class TrackParameters {

    private int instrument;
    private int volume;
    private int octave;

    public TrackParameters(int volume, int octave, int instrument) {
        this.volume = volume;
        this.octave = octave;
        this.instrument = instrument;
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
        return "TrackParameters [instrument=" + instrument + ", volume=" + volume + ", octave=" + octave + "]";
    }
}
