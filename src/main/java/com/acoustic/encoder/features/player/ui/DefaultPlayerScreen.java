package com.acoustic.encoder.features.player.ui;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;

public class DefaultPlayerScreen implements PlayerScreen {

    private final AudioPlayerController playerController;

    private final PlayerViewManagerFactory managerFactory;

    private PlayerViewManager manager;

    private final EventBus eventBus;

    public DefaultPlayerScreen(
            AudioPlayerController playerController,
            PlayerViewManagerFactory managerFactory,
            EventBus eventBus
    ) {

        if (playerController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.playerController = playerController;

        if (managerFactory == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.managerFactory = managerFactory;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

        setEvents();

        initialize();
    }

    @Override
    public void initialize() {
        this.manager = managerFactory.createViewManager(this.playerController);
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
        //this.frame.disposeFrame();
        this.manager.disposeFrame();
    }

    private void setEvents() {
        eventBus.subscribe(AppShutdownEvent.class, event -> closeWindow());
        eventBus.subscribe(
                PlayerClosedEvent.class, event -> hideWindow());
    }

}
