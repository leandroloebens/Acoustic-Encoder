package com.acoustic.encoder.features.player.ui.swing.components.factory;

import com.acoustic.encoder.features.player.ui.swing.components.MusicProgressBarPanel;
import com.acoustic.encoder.features.player.ui.swing.components.PlayPauseButton;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.ProgressSliderUI;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
import com.acoustic.encoder.infrastructure.ui_shared.swing.icons.IconLoader;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

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

        PlayPauseButton playPauseButton = createPlayPauseButton(
                IconLoader.load(
                        config.getString("PLAY_BUTTON_ICON_PATH"),
                        config.getScaledDimension("PLAY_BUTTON_ICON_SIZE")
                ),

                IconLoader.load(
                        config.getString("PAUSE_BUTTON_ICON_PATH"),
                        config.getScaledDimension("PAUSE_BUTTON_ICON_SIZE")
                )
        );

        SwingButton forwardButton =
                createForwardButton(IconLoader.load(
                        config.getString("FORWARD_BUTTON_ICON_PATH"),
                        config.getScaledDimension("FORWARD_BUTTON_ICON_SIZE")
                        )
                );

        SwingButton backwardButton =
                createBackwardButton(IconLoader.load(
                        config.getString("REWIND_BUTTON_ICON_PATH"),
                        config.getScaledDimension("REWIND_BUTTON_ICON_SIZE")
                        )
                );

        SwingButton saveMusicButton =
                createSaveMusicButton(IconLoader.load(
                        config.getString("SAVE_BUTTON_ICON_PATH"),
                        config.getScaledDimension("SAVE_BUTTON_ICON_SIZE")
                        )
                );

        SwingUtils.setHandCursor(playPauseButton, forwardButton, backwardButton, saveMusicButton);

        return new PlayerViewComponentsWrapper(
                playPauseButton,
                forwardButton,
                backwardButton,
                createProgressBar(),
                saveMusicButton
        );
    }

    private PlayPauseButton createPlayPauseButton(Icon playIcon, Icon pauseIcon) {
        PlayPauseButton button = new PlayPauseButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                null,
                config.getScaledDimension("PLAY_PAUSE_BUTTON_PREFERRED_SIZE"),
                playIcon,
                pauseIcon
        );

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    private SwingButton createBackwardButton(Icon rewindIcon) {
        SwingButton button = new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                rewindIcon,
                null,
                config.getScaledDimension("SKIP_BUTTONS_PREFERRED_SIZE")
        );

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    private SwingButton createForwardButton(Icon forwardIcon) {
        SwingButton button = new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                forwardIcon,
                null,
                config.getScaledDimension("SKIP_BUTTONS_PREFERRED_SIZE")
        );

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    private SwingButton createSaveMusicButton(Icon saveIcon) {
        SwingButton button = new SwingButton(
                null,
                null,
                config.getScaledInt("NULL_TEXT_BUTTON_FONT_SIZE"),
                saveIcon,
                null,
                config.getScaledDimension("SAVE_BUTTON_PREFERRED_SIZE")
        );

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    private MusicProgressBarPanel createProgressBar() {
        SwingSeekBar progressBar = createSeekBar();
        progressBar.setUI(new ProgressSliderUI());
        progressBar.setPaintTicks(false);
        progressBar.setPaintLabels(false);
        progressBar.setOpaque(false);

        SwingLabel currentTimeLabel = new SwingLabel(
                null,
                null,
                config.getScaledInt("PROGRESS_TIME_LABEL_FONT_SIZE"),
                null
        );

        SwingLabel totalTimeLabel = new SwingLabel(
                null,
                null,
                config.getScaledInt("PROGRESS_TIME_LABEL_FONT_SIZE"),
                null
        );

        return new MusicProgressBarPanel(progressBar, currentTimeLabel, totalTimeLabel);
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
}