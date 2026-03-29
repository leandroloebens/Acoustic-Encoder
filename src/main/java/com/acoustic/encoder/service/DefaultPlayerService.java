package com.acoustic.encoder.service;

import com.acoustic.encoder.audio.AudioPlayer;
import com.acoustic.encoder.model.MusicModel;

public class DefaultPlayerService implements AudioPlayerService {

    private final AudioPlayer player;

    public DefaultPlayerService(AudioPlayer player) {

        this.player = player;
    }

    public void loadMusic(MusicModel musicModel) throws Exception {
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
