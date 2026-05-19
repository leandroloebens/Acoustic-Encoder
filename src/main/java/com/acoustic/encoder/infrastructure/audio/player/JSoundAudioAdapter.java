package com.acoustic.encoder.infrastructure.audio.player;

import com.acoustic.encoder.features.player.ports.AudioPlayer;
import com.acoustic.encoder.domain.music.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

public class JSoundAudioAdapter implements AudioPlayer {

    private final SequenceBuilder builder;
    private final SequencePlayer sequencePlayer;

    public JSoundAudioAdapter(SequenceBuilder builder, SequencePlayer sequencePlayer) {

        this.builder = builder;
        this.sequencePlayer = sequencePlayer;
    }

    @Override
    public void loadMusic(MusicModel musicModel) throws InvalidMidiDataException {

        Sequence sequence = this.builder.buildSequence(musicModel);
        this.sequencePlayer.loadSequence(sequence);
    }

    @Override
    public void play() {

        this.sequencePlayer.play();
    }

    @Override
    public void stop() {

        this.sequencePlayer.stop();
    }

    @Override
    public void rewind() {
        this.sequencePlayer.rewind();
    }

    @Override
    public void close() {
        sequencePlayer.closeSequencer();
    }

    @Override
    public long getMicrosecPosition() {
        return sequencePlayer.getMicrosecPosition();
    }

    @Override
    public long getMicrosecDuration() {
        return sequencePlayer.getMicrosecDuration();
    }

    @Override
    public void seekMusic(long microsecPosition) {
        sequencePlayer.setMicrosecPosition(microsecPosition);
    }
}
