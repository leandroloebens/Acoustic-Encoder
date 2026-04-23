package com.acoustic.encoder.shared.navigation;

import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.player.view.PlayerScreen;
import com.acoustic.encoder.shared.factory.ScreenFactory;

public class DefaultAppNavigator implements AppNavigator {

    private final ConversionScreen conversionScreen;

    private final PlayerScreen playerScreen;

    public DefaultAppNavigator(ScreenFactory factory) {
        // NOTE: 'this' is passed during construction.
        // The factory must only store the reference, never invoke methods on the observer at this point.
        this.conversionScreen = factory.createConversionScreen();
        this.playerScreen = factory.createPlayerScreen();

    }

    public void startApp() {

        this.conversionScreen.startFrame();

    }

    @Override
    public void displayConversionScreen() {

        this.conversionScreen.showFrame();

    }

    public void displayPlayerScreen() {

        this.playerScreen.startFrame();

    }

}
