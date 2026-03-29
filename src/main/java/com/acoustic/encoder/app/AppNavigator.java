package com.acoustic.encoder.app;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AppNavigator {

    void startApp();

    void displayPlayerScreen(MusicModel musicModel) throws InvalidMidiDataException;

}
