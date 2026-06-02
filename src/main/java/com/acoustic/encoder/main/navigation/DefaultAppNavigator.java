package com.acoustic.encoder.main.navigation;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpenEvent;
import com.acoustic.encoder.features.conversion.event.ConversionCloseRequestEvent;
import com.acoustic.encoder.features.conversion.ui.ConversionScreen;
import com.acoustic.encoder.features.player.ui.PlayerScreen;
import com.acoustic.encoder.features.start.event.StartCloseRequestEvent;
import com.acoustic.encoder.features.start.ui.StartScreen;
import com.acoustic.encoder.main.factory.ScreenFactory;
import com.acoustic.encoder.main.navigation.listener.NavConversionCloseRequestListener;
import com.acoustic.encoder.main.navigation.listener.NavConversionCompletedListener;
import com.acoustic.encoder.main.navigation.listener.NavProjectReadyToOpenListener;
import com.acoustic.encoder.main.navigation.listener.NavStartCloseRequestListener;


public class DefaultAppNavigator implements AppNavigator {

    private static final int SYSTEM_OUT_ONCLOSE_STATUS = 0;

//    ScreenFactory factory;

    private StartScreen startScreen;

    private final ConversionScreen conversionScreen;

    private final PlayerScreen playerScreen;

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
        // After starting up the conversion screen, there's no need to keep the start screen reference
        // (Start screen is always closed/disposed by its own, this only removes it's reference, allowing it to be
        // destroyed and collected by the Garbage Collector)
        if (this.startScreen != null) this.startScreen = null;

        this.conversionScreen.showWindow();
    }

    @Override
    public void displayPlayerScreen() {
        this.playerScreen.showWindow();
    }

    private void setEvents() {
        eventBus.subscribeOnUiThread(
                StartCloseRequestEvent.class, new NavStartCloseRequestListener(this)
        );
        eventBus.subscribeOnUiThread(
                ProjectReadyToOpenEvent.class, new NavProjectReadyToOpenListener(this)
        );
        eventBus.subscribeOnUiThread(
                ConversionCompletedEvent.class, new NavConversionCompletedListener(this)
        );
        eventBus.subscribeOnUiThread(
                ConversionCloseRequestEvent.class, new NavConversionCloseRequestListener(this)
        );
    }

}
