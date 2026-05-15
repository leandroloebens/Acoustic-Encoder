package com.acoustic.encoder.features.player.view.swing.components.factory;

import com.acoustic.encoder.features.player.view.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.view.swing.components.PlayerFooterComponent;
import com.acoustic.encoder.features.player.view.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;

import java.util.HashMap;

public class DefaultSwingPlayerViewComponentsFactory implements SwingPlayerViewComponentsFactory {

    private final static String ILLEGAL_CONFIG_ARGUMENT_MESSAGE = "Illegal config argument!";

    private final SwingViewConfigWrapper config;

    public DefaultSwingPlayerViewComponentsFactory(HashMap<String, String> configMap) {
        if (configMap == null) throw new IllegalArgumentException(ILLEGAL_CONFIG_ARGUMENT_MESSAGE);
        this.config = new SwingViewConfigWrapper(configMap);
    }

    @Override
    public PlayerViewComponentsWrapper createComponents() {

        PlayerControlsComponent controlsComponent = new PlayerControlsComponent();
        PlayerFooterComponent footerComponent = new PlayerFooterComponent();

        return new PlayerViewComponentsWrapper(
                controlsComponent,
                footerComponent
        );
    }
}
