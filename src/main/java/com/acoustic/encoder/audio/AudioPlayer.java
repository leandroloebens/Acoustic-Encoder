package com.acoustic.encoder.audio;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AudioPlayer {

    void loadMusic(MusicModel musicModel) throws InvalidMidiDataException;

    void play();

    void stop();

    void rewind();
}
