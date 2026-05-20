package com.acoustic.encoder.features.player.ports;

import com.acoustic.encoder.domain.music.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AudioPlayer {

    void loadMusic(MusicModel musicModel) throws InvalidMidiDataException;

    void play();

    void stop();

    void rewind();

    void close();

    long getMicrosecPosition();

    long getMicrosecDuration();

    void seekMusic(long microsecPosition);

    boolean isPlaying();
}
