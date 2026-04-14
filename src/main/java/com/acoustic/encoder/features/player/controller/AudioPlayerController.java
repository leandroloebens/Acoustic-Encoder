package com.acoustic.encoder.features.player.controller;

import com.acoustic.encoder.shared.model.MusicModel;

public interface AudioPlayerController {

    void handleLoadAction(MusicModel musicModel);

    void handlePlayAction();

    void handlePauseAction();

    void handleRewindAction();
}
