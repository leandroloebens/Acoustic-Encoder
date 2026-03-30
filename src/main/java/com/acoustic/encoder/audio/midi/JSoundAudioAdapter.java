package com.acoustic.encoder.audio.midi;

import com.acoustic.encoder.audio.AudioPlayer;
import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public class JSoundAudioAdapter implements AudioPlayer {

    private final SequenceBuilder builder;
    private final SequencePlayer sequencePlayer;

    public JSoundAudioAdapter(SequenceBuilder builder, SequencePlayer sequencePlayer) {

        this.builder = builder;
        this.sequencePlayer = sequencePlayer;
    }

    public void loadMusic(MusicModel musicModel) throws InvalidMidiDataException {

        Sequence sequence = this.builder.buildSequence(musicModel);
        this.sequencePlayer.loadSequence(sequence);
        this.sequencePlayer.setBpm(musicModel.config().bpm());

    }

    public void play() {

        this.sequencePlayer.play();
    }

    public void stop() {

        this.sequencePlayer.stop();
    }

    public void rewind() {

    }
}
