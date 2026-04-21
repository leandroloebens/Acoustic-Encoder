package com.acoustic.encoder.features.player.service;

import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.shared.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;

public interface AudioPlayerService {

    void setPlayerMusic(MusicModel musicModel) throws IllegalArgumentException, InvalidMidiDataException;

    void playMusic();

    void stopMusic();

    void rewindMusic();

    void exportMusic(File destination) throws MusicExportException;
}
