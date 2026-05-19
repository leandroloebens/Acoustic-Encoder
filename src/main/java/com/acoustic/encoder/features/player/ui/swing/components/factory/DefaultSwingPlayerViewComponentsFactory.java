package com.acoustic.encoder.features.player.ui.swing.components.factory;

import com.acoustic.encoder.features.player.ui.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.ui.swing.components.PlayerFooterComponent;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.icons.IconLoader;

import javax.swing.*;
import java.awt.*;
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

        return new PlayerViewComponentsWrapper(
                createControlsComponent(),
                createFooterComponent()
        );
    }

    private PlayerControlsComponent createControlsComponent() {
        Icon playIcon = IconLoader.load("/ui/icons/play.png");
        Icon pauseIcon = IconLoader.load("/ui/icons/pause.png");

        createPlayPauseButton(playIcon);

        return new PlayerControlsComponent(
                createPlayPauseButton(playIcon),
                createRewindButton(),
                playIcon,
                pauseIcon
        );
    }

    private PlayerFooterComponent createFooterComponent() {
        Icon saveIcon = IconLoader.load("/ui/icons/download.png");

        createSaveButton(saveIcon);

        return new PlayerFooterComponent(
                createSaveButton(saveIcon),
                saveIcon
        );
    }

    private SwingButton createPlayPauseButton(Icon playIcon) {

        return new SwingButton(
                config.getString("PLAY_BUTTON_TEXT"),
                playIcon,
                null,
                20,
                null,
                null
        );
    }

    private SwingButton createRewindButton() {

        return new SwingButton(
                config.getString("REWIND_BUTTON_TEXT"),
                null,
                20,
                null,
                null
        );
    }

    private SwingButton createSaveButton(Icon saveIcon) {

        return new SwingButton(
                config.getString("SAVE_BUTTON_TEXT"),
                saveIcon,
                null,
                20,
                null,
                null
        );
    }
}
