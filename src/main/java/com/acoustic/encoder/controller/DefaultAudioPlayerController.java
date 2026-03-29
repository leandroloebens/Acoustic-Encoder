package com.acoustic.encoder.controller;

import com.acoustic.encoder.model.MusicModel;
import com.acoustic.encoder.service.AudioPlayerService;

import javax.sound.midi.InvalidMidiDataException;

public class DefaultAudioPlayerController implements AudioPlayerController{

    private final AudioPlayerService playerService;

    public DefaultAudioPlayerController(AudioPlayerService playerService, MusicModel musicModel) {

        this.playerService = playerService;

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
