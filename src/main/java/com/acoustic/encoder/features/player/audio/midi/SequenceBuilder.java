package com.acoustic.encoder.features.player.audio.midi;

import com.acoustic.encoder.shared.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public interface SequenceBuilder {

    Sequence buildSequence(MusicModel musicModel) throws InvalidMidiDataException;

}
