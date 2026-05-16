package com.acoustic.encoder.infrastructure.audio.player;

import com.acoustic.encoder.domain.music.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public interface SequenceBuilder {

    Sequence buildSequence(MusicModel musicModel) throws InvalidMidiDataException;

}
