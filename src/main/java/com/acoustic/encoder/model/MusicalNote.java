package com.acoustic.encoder.model;

public class MusicalNote {

    private Pitch pitch;

    private int octave;

    public MusicalNote(Pitch pitch, int octave) {
        this.pitch = pitch;
        this.octave = octave;
    }

    public Pitch getPitch() {
        return this.pitch;
    }

    public int getOctave() {
        return this.octave;
    }

    public void setPitch(Pitch pitch) {
        this.pitch = pitch;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }
}
