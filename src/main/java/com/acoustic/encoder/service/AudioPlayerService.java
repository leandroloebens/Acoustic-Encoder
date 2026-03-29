package com.acoustic.encoder.service;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AudioPlayerService {

    void setPlayerMusic(MusicModel musicModel) throws IllegalArgumentException, InvalidMidiDataException;

    void playMusic();

    void stopMusic();

    void rewindMusic();
}
