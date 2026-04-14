package com.acoustic.encoder.features.player.controller;

import com.acoustic.encoder.shared.model.MusicModel;
import com.acoustic.encoder.features.player.service.AudioPlayerService;

import javax.sound.midi.InvalidMidiDataException;

public class DefaultAudioPlayerController implements AudioPlayerController {

    private final AudioPlayerService playerService;

    public DefaultAudioPlayerController(AudioPlayerService playerService) {

        this.playerService = playerService;

    }

    public void handleLoadAction(MusicModel musicModel) {
        try {
            this.playerService.setPlayerMusic(musicModel);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }

    public void handlePlayAction() {

        playerService.playMusic();
    }

    public void handlePauseAction() {

        playerService.stopMusic();
    }

    public void handleRewindAction() {

        playerService.rewindMusic();
    }
}
