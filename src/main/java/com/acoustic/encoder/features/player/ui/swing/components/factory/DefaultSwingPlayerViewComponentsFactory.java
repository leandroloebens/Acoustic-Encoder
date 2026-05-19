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

    private final int lado = 40;

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
        Icon playIcon = IconLoader.load("/ui/icons/play_filled_32.png");
        Icon pauseIcon = IconLoader.load("/ui/icons/pause_filled_32.png");
        Icon rewindIcon = IconLoader.load("/ui/icons/fast_rewind_B3B3B3.png");
        Icon forwardIcon = IconLoader.load("/ui/icons/fast_forward_B3B3B3.png");

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
                createProgressBar()
        );
    }

    private SwingButton createPlayPauseButton(Icon playIcon) {

        return new SwingButton(
                null,
                playIcon,
                null,
                20,
                null,
                new Dimension(50, 50)
        );
    }

    private SwingButton createRewindButton(Icon rewindIcon) {

        return new SwingButton(
                null,
                rewindIcon,
                null,
                20,
                null,
                new Dimension(lado, lado)
        );
    }

    private SwingButton createForwardButton(Icon forwardIcon) {

        return new SwingButton(
                null,
                forwardIcon,
                null,
                20,
                null,
                new Dimension(lado, lado)
        );
    }

    private SwingButton createSaveButton(Icon saveIcon) {

        return new SwingButton(
                null,
                saveIcon,
                null,
                20,
                null,
                null
        );
    }

    private JProgressBar createProgressBar() {

        return new JProgressBar();
    }
}
