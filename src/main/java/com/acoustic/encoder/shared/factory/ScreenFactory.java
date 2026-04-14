package com.acoustic.encoder.shared.factory;

import com.acoustic.encoder.features.conversion.view.ConversionScreen;
import com.acoustic.encoder.features.player.view.PlayerScreen;

public interface ScreenFactory {

    ConversionScreen createConversionScreen();

    PlayerScreen createPlayerScreen();
}
