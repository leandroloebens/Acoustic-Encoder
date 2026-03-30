package com.acoustic.encoder.audio.midi;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public interface SequenceBuilder {

    Sequence buildSequence(MusicModel musicModel) throws InvalidMidiDataException;

}
