package com.acoustic.encoder.features.player.ui.swing.components.factory;

import com.acoustic.encoder.features.player.ui.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.ui.swing.components.PlayerFooterComponent;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
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
        Icon playIcon = IconLoader.load(config.getString("PLAY_BUTTON_ICON_PATH"));
        Icon pauseIcon = IconLoader.load(config.getString("PAUSE_BUTTON_ICON_PATH"));
        Icon rewindIcon = IconLoader.load(config.getString("REWIND_BUTTON_ICON_PATH"));
        Icon forwardIcon = IconLoader.load(config.getString("FORWARD_BUTTON_ICON_PATH"));

        return new PlayerControlsComponent(
                createPlayPauseButton(playIcon),
                createRewindButton(rewindIcon),
                createForwardButton(forwardIcon),
                playIcon,
                pauseIcon
        );
    }

    private PlayerFooterComponent createFooterComponent() {
        Icon saveIcon = IconLoader.load(config.getString("SAVE_BUTTON_ICON_PATH"));

        return new PlayerFooterComponent(
                createSaveButton(saveIcon),
                createSeekBar(),
                createCurrentTimeLabel(),
                createDurationTimeLabel()
        );
    }

    private SwingButton createPlayPauseButton(Icon playIcon) {

        return new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                playIcon,
                null,
                config.getScaledDimension("PLAY_PAUSE_BUTTON_PREFERRED_SIZE")
        );
    }

    private SwingButton createRewindButton(Icon rewindIcon) {

        return new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                rewindIcon,
                null,
                config.getScaledDimension("SKIP_BUTTONS_PREFERRED_SIZE")
        );
    }

    private SwingButton createForwardButton(Icon forwardIcon) {

        return new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                forwardIcon,
                null,
                config.getScaledDimension("SKIP_BUTTONS_PREFERRED_SIZE")
        );
    }

    private SwingButton createSaveButton(Icon saveIcon) {

        return new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                saveIcon,
                null,
                config.getScaledDimension("SAVE_BUTTON_PREFERRED_SIZE")
        );
    }

    private SwingSeekBar createSeekBar() {

        return new SwingSeekBar(
                config.getScaledInt("SEEKBAR_DIRECTION"),
                config.getScaledInt("SEEKBAR_MIN"),
                config.getScaledInt("SEEKBAR_MIN"),
                config.getScaledInt("SEEKBAR_MAX"),
                config.getScaledInt("SEEKBAR_MAX"),
                config.getScaledInt("SEEKBAR_START_VALUE"),
                config.getScaledInt("SEEKBAR_TICK_SPACING"),
                null,
                null
        );
    }

    private SwingLabel createCurrentTimeLabel() {

        return new SwingLabel(
                null,
                null,
                config.getScaledInt("PROGRESS_TIME_LABEL_FONT_SIZE"),
                null
        );
    }

    private SwingLabel createDurationTimeLabel() {

        return new SwingLabel(
                null,
                null,
                config.getScaledInt("PROGRESS_TIME_LABEL_FONT_SIZE"),
                null
        );
    }
}
