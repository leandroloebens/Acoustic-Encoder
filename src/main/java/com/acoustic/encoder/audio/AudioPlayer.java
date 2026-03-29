package com.acoustic.encoder.audio;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public interface AudioPlayer {

    public void loadMusic(MusicModel musicModel) throws InvalidMidiDataException;

    public void play();

    public void stop();

    public void rewind();
}
