package com.acoustic.encoder.controller;

import com.acoustic.encoder.model.MusicModel;
import com.acoustic.encoder.service.AudioPlayerService;

public class DefaultPlayerController implements AudioPlayerController{

    private final AudioPlayerService playerService;
    private MusicModel musicModel;

    public DefaultPlayerController(AudioPlayerService playerService) {

        this.playerService = playerService;
    }

    public void handlePlayAction(MusicModel musicModel) {

        playerService.playMusic();
    }

    public void handlePauseAction() {

        playerService.stopMusic();
    }

    public void handleRewindAction() {

        playerService.rewindMusic();
    }
}
