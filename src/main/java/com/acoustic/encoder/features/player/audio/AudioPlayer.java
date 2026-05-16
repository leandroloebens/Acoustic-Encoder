package com.acoustic.encoder.features.player.audio;

import com.acoustic.encoder.shared.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AudioPlayer {

    void loadMusic(MusicModel musicModel) throws InvalidMidiDataException;

    void play();

    void stop();

    void rewind();

    void close();
}
