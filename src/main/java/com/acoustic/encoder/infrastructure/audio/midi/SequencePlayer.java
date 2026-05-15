package com.acoustic.encoder.infrastructure.audio.midi;

import com.acoustic.encoder.infrastructure.audio.export.midi.SequenceProvider;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public interface SequencePlayer extends SequenceProvider {

    void loadSequence(Sequence sequence) throws InvalidMidiDataException;

    void play();

    void stop();

    void rewind();

    void closeSequencer();
}
