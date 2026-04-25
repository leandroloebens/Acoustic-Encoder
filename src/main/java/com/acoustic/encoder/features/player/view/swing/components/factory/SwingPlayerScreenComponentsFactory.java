package com.acoustic.encoder.features.player.view.swing.components.factory;

import com.acoustic.encoder.features.player.view.PlayerControlsComponent;
import com.acoustic.encoder.features.player.view.PlayerFooterComponent;
import com.acoustic.encoder.features.player.view.swing.components.config.SwingPlayerConfig;
import com.acoustic.encoder.features.player.view.swing.components.dto.PlayerScreenComponentsWrapper;

import java.util.HashMap;

public class SwingPlayerScreenComponentsFactory implements PlayerScreenComponentsFactory {

    private final static String ILLEGAL_CONFIG_ARGUMENT_MESSAGE = "Illegal config argument!";

    private final SwingPlayerConfig config;

    public SwingPlayerScreenComponentsFactory(HashMap<String, String> configMap) {
        if (configMap == null) throw new IllegalArgumentException(ILLEGAL_CONFIG_ARGUMENT_MESSAGE);
        this.config = new SwingPlayerConfig(configMap);
    }

    @Override
    public PlayerScreenComponentsWrapper createComponents() {

        PlayerControlsComponent controlsComponent = new PlayerControlsComponent();
        PlayerFooterComponent footerComponent = new PlayerFooterComponent();

        return new PlayerScreenComponentsWrapper(
                controlsComponent,
                footerComponent
        );
    }
}
