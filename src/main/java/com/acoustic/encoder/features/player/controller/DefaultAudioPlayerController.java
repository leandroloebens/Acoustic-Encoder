package com.acoustic.encoder.features.player.controller;

import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.shared.model.MusicModel;
import com.acoustic.encoder.features.player.service.AudioPlayerService;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;

public class DefaultAudioPlayerController implements AudioPlayerController {

    private final AudioPlayerService playerService;

    public DefaultAudioPlayerController(AudioPlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void handleLoadAction(MusicModel musicModel) {
        try {
            this.playerService.setPlayerMusic(musicModel);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
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
    public void handleRewindAction() {
        playerService.rewindMusic();
    }

    @Override
    public void handleSaveAction(File destination) throws MusicExportException {
        playerService.exportMusic(destination);
    }
}
