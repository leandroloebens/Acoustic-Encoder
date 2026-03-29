package com.acoustic.encoder.audio;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public interface SequencePlayer {

    public void loadSequence(Sequence sequence) throws InvalidMidiDataException;

    public void setBpm(int bpm);

    public void play();

    public void stop();

    public void rewind();
}
