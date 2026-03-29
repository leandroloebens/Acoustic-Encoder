package com.acoustic.encoder.service;

import com.acoustic.encoder.audio.AudioPlayer;
import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.InvalidMidiDataException;

public class DefaultAudioPlayerService implements AudioPlayerService {

    private final AudioPlayer player;

    public DefaultAudioPlayerService(AudioPlayer player) {

        this.player = player;
    }

    public void setPlayerMusic(MusicModel musicModel) throws IllegalArgumentException, InvalidMidiDataException {
        player.loadMusic(musicModel);
    }

    public void playMusic() {
        player.play();
    }

    public void stopMusic() {
        player.stop();
    }

    public void rewindMusic() {
        player.rewind();
    }
}
