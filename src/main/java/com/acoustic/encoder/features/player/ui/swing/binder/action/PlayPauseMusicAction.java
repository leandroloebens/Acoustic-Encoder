package com.acoustic.encoder.features.player.ui.swing.binder.action;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.ui.swing.components.PlayPauseButton;

public class PlayPauseMusicAction implements Runnable {

    private final PlayPauseButton playPauseButton;
    private final AudioPlayerController controller;

    public PlayPauseMusicAction(PlayPauseButton button, AudioPlayerController controller) {
        if (button == null) throw new IllegalArgumentException("button cannot be null");
        this.playPauseButton = button;

        if (controller == null) throw new IllegalArgumentException("controller cannot be null");
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.handlePlayPauseToggleAction();
        playPauseButton.setPlayPauseState(controller.isPlayingAudio());
    }
}
