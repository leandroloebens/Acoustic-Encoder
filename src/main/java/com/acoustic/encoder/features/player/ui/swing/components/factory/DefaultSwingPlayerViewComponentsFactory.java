package com.acoustic.encoder.features.player.ui.swing.components.factory;

import com.acoustic.encoder.features.player.ui.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.ui.swing.components.PlayerFooterComponent;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
import com.acoustic.encoder.infrastructure.ui_shared.swing.icons.IconLoader;

import javax.swing.*;
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
        Icon rewindIcon = IconLoader.load("/ui/icons/rewind.png");
        Icon forwardIcon = IconLoader.load("/ui/icons/forward.png");

        return new PlayerControlsComponent(
                createPlayPauseButton(playIcon),
                createRewindButton(rewindIcon),
                createForwardButton(forwardIcon),
                playIcon,
                pauseIcon
        );
    }

    private PlayerFooterComponent createFooterComponent() {
        Icon saveIcon = IconLoader.load("/ui/icons/download.png");

        return new PlayerFooterComponent(
                createSaveButton(saveIcon),
                createSeekBar()
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

    private SwingButton createRewindButton(Icon rewindIcon) {

        return new SwingButton(
                config.getString("REWIND_BUTTON_TEXT"),
                rewindIcon,
                null,
                20,
                null,
                null
        );
    }

    private SwingButton createForwardButton(Icon forwardIcon) {

        return new SwingButton(
                config.getString("FORWARD_BUTTON_TEXT"),
                forwardIcon,
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

    private SwingSeekBar createSeekBar() {

        return new SwingSeekBar(
                0,
                0,
                0,
                1000000,
                1000000,
                10,
                1000000,
                null,
                null
        );
    }
}
