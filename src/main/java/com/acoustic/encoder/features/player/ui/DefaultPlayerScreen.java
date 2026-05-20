package com.acoustic.encoder.features.player.ui;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;

public class DefaultPlayerScreen implements PlayerScreen {

    private final AudioPlayerController playerController;

    private final PlayerViewManager manager;

    public DefaultPlayerScreen(AudioPlayerController playerController, PlayerViewManager manager) {

        if (playerController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.playerController = playerController;

        if (manager == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.manager = manager;

        initialize();
    }

    @Override
    public void initialize() {
        this.manager.startFrame(this.playerController);
    }

    @Override
    public void showWindow() {
        this.manager.showFrame();
    }

    @Override
    public void hideWindow() {
        //this.frame.setVisible(false);
        this.manager.hideFrame();
    }

    @Override
    public void closeWindow() {
        //this.frame.dispose();
        this.manager.disposeFrame();
    }

}
