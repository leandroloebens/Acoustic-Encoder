package com.acoustic.encoder.shared.navigation;

import com.acoustic.encoder.features.conversion.event.ConversionClosedEvent;
import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.view.PlayerScreen;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.factory.ScreenFactory;

public class DefaultAppNavigator implements AppNavigator {

    private static final int SYSTEM_OUT_ONCLOSE_STATUS = 0;

    private final ConversionScreen conversionScreen;

    private final PlayerScreen playerScreen;

    private final EventBus eventBus;

    public DefaultAppNavigator(ScreenFactory factory, EventBus eventBus) {
        // NOTE: 'this' is passed during construction.
        // The factory must only store the reference, never invoke methods on the observer at this point.
        this.conversionScreen = factory.createConversionScreen();
        this.playerScreen = factory.createPlayerScreen();

        if (eventBus == null) throw new IllegalArgumentException(("EventBust cannot be null!"));
        this.eventBus = eventBus;

        eventBus.subscribe(PlayerClosedEvent.class, event -> displayConversionScreen());
        eventBus.subscribe(ConversionClosedEvent.class, event -> closeApp());
    }

    @Override
    public void startApp() {

        this.conversionScreen.startFrame();

    }

    @Override
    public void closeApp() {

        System.exit(SYSTEM_OUT_ONCLOSE_STATUS);

    }

    @Override
    public void displayConversionScreen() {

        this.conversionScreen.showFrame();

    }

    @Override
    public void displayPlayerScreen() {

        this.playerScreen.startFrame();

    }

}
