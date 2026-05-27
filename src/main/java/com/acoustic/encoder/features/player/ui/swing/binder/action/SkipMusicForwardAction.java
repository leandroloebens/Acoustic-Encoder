package com.acoustic.encoder.features.player.ui.swing.binder.action;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;

public class SkipMusicForwardAction implements Runnable {

    private final AudioPlayerController controller;

    public SkipMusicForwardAction(AudioPlayerController controller) {
        if (controller == null) throw new IllegalArgumentException("Audio player controller cannot be null");
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.handleSkipForwardAction();
    }
}
