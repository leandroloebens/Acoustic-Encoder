package com.acoustic.encoder.shared.navigation;

import com.acoustic.encoder.features.conversion.event.ConversionScreenClosedEvent;
import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.view.PlayerScreen;
import com.acoustic.encoder.features.start.event.StartScreenCloseRequestEvent;
import com.acoustic.encoder.features.start.event.StartScreenClosedEvent;
import com.acoustic.encoder.features.start.view.StartScreen;
import com.acoustic.encoder.shared.event.AppShutdownEvent;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.event.ProjectReadyToOpen;
import com.acoustic.encoder.shared.factory.ScreenFactory;

public class DefaultAppNavigator implements AppNavigator {

    private static final int SYSTEM_OUT_ONCLOSE_STATUS = 0;

//    ScreenFactory factory;

    private StartScreen startScreen;

    private ConversionScreen conversionScreen;

    private PlayerScreen playerScreen;

    private final EventBus eventBus;

    public DefaultAppNavigator(ScreenFactory factory, EventBus eventBus) {
        // NOTE: 'this' is passed during construction.
        // The factory must only store the reference, never invoke methods on the observer at this point.
        //this.startScreen = factory.createStartScreen();
//        this.factory = factory;

        this.startScreen = factory.createStartScreen();
        this.conversionScreen = factory.createConversionScreen();
        this.playerScreen = factory.createPlayerScreen();

        if (eventBus == null) throw new IllegalArgumentException(("EventBus cannot be null!"));
        this.eventBus = eventBus;

        setEvents();
    }

    @Override
    public void startApp() {
        this.displayStartScreen();
    }

    @Override
    public void closeApp() {
        // Publish shutdown event to notify all components that the app is closing,
        // allowing them to perform any necessary cleanup.
        eventBus.publish(new AppShutdownEvent());

        System.exit(SYSTEM_OUT_ONCLOSE_STATUS);
    }

    @Override
    public void displayStartScreen() {
        this.startScreen.showWindow();
    }

    @Override
    public void displayConversionScreen() {
        this.conversionScreen.showWindow();
    }

    @Override
    public void displayPlayerScreen() {
        this.playerScreen.startFrame();
    }

    private void destroyStartScreen() {
        this.startScreen = null;
        this.displayConversionScreen();
    }

    private void setEvents() {
        eventBus.subscribe(StartScreenCloseRequestEvent.class, event -> closeApp());
        eventBus.subscribe(ProjectReadyToOpen.class, event -> destroyStartScreen());
        eventBus.subscribe(ConversionScreenClosedEvent.class, event -> closeApp());
    }

}
