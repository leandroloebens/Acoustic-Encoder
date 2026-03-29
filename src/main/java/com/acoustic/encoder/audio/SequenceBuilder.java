package com.acoustic.encoder.audio;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.Sequence;

public interface SequenceBuilder {

    public Sequence buildSequence(MusicModel musicModel);

}
