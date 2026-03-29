package com.acoustic.encoder.controller;

import com.acoustic.encoder.model.MusicModel;

public interface AudioPlayerController {

    public void handlePlayAction(MusicModel musicModel);

    public void handlePauseAction();

    public void handleRewindAction();
}
