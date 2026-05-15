package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.ui.ConversionScreen;
import com.acoustic.encoder.features.player.ui.PlayerScreen;
import com.acoustic.encoder.features.start.ui.StartScreen;

public interface ScreenFactory {

    StartScreen createStartScreen();

    ConversionScreen createConversionScreen();

    PlayerScreen createPlayerScreen();
}
