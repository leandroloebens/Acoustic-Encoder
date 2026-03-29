package com.acoustic.encoder.app;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AppNavigator {

    public void startApp();

    public void displayPlayerScreen(MusicModel musicModel) throws InvalidMidiDataException;

}
