package com.acoustic.encoder.audio.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public interface SequencePlayer {

    void loadSequence(Sequence sequence) throws InvalidMidiDataException;

    void setBpm(int bpm);

    void play();

    void stop();

    void rewind();
}
