package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.model.MusicModel;

public class DefaultPlayerScreen implements PlayerScreen {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static int BUTTON_PANEL_TGAP = 10;
    private final static int BUTTON_PANEL_LGAP = 10;
    private final static int BUTTON_PANEL_BGAP = 10;
    private final static int BUTTON_PANEL_RGAP = 10;

    private final AudioPlayerController playerController;

    private final PlayerViewManager manager;

    public DefaultPlayerScreen(AudioPlayerController playerController, PlayerViewManager manager) {

        if (playerController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.playerController = playerController;

        if (manager == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.manager = manager;

    }

    @Override
    public void startFrame() {
        //this.frame.setVisible(true);
        this.manager.startFrame(this.playerController);
    }

    @Override
    public void closeFrame() {
        //this.frame.setVisible(false);
        this.manager.hideFrame();
    }

    @Override
    public void loadMusic(MusicModel musicModel) {
        this.playerController.handleLoadAction(musicModel);
    }
}
