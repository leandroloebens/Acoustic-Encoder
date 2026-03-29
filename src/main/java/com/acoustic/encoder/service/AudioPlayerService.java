package com.acoustic.encoder.service;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AudioPlayerService {

    public void loadMusic(MusicModel musicModel) throws InvalidMidiDataException, Exception;

    public void playMusic();

    public void stopMusic();

    public void rewindMusic();
}
