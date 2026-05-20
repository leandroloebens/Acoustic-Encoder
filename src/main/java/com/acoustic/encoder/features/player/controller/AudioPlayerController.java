package com.acoustic.encoder.features.player.controller;

import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.domain.music.MusicModel;

import java.io.File;

public interface AudioPlayerController {

    void handleLoadAction(MusicModel musicModel);

    void handlePlayAction();

    void handlePauseAction();

    void handleRewindAction();

    void handleSaveAction(File destination) throws MusicExportException;

    long getMicrosecPosition();

    long getMicrosecDuration();

    void handleSeekAction(long microsecPosition);

    boolean isPlayingAudio();

    void handlePlayPauseToggleAction();

}
