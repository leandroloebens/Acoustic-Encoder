package com.acoustic.encoder.features.player.controller;

import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.service.AudioPlayerService;

import java.io.File;

public class DefaultAudioPlayerController implements AudioPlayerController {

    private static final double DEFAULT_SKIP_PERCENTAGE = 10.0;

    private final AudioPlayerService playerService;

    public DefaultAudioPlayerController(AudioPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void handlePlayAction() {
        playerService.playMusic();
    }

    @Override
    public void handlePauseAction() {
        playerService.stopMusic();
    }

    @Override
    public void handleSkipBackwardAction() {
        playerService.seekRelativeDurationPercent(-DEFAULT_SKIP_PERCENTAGE);
    }

    @Override
    public void handleSkipForwardAction() {
        playerService.seekRelativeDurationPercent(DEFAULT_SKIP_PERCENTAGE);
    }

    @Override
    public void handleSaveAction(File destination) throws MusicExportException {
        playerService.exportMusic(destination);
    }

    @Override
    public long getMicrosecPosition() {
        return playerService.getMicrosecPosition();
    }

    @Override
    public long getMicrosecDuration() {
        return playerService.getMicrosecDuration();
    }

    @Override
    public void handleSeekAction(long microsecPosition) {
        playerService.seekMusic(microsecPosition);
    }

    @Override
    public boolean isPlayingAudio() {
        return playerService.isPlayingAudio();
    }

    @Override
    public void handlePlayPauseToggleAction() {
        if (playerService.isPlayingAudio()) {
            playerService.stopMusic();
        } else {
            playerService.playMusic();
        }
    }


}
