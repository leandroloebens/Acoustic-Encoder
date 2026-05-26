package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingRoundedPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerControlsComponent extends JPanel {

    private static final int CONTROLS_BUTTONS_HGAP = (int) (35 * SwingUtils.getScreenScaleRatio());
    private static final int CONTROLS_BUTTONS_VGAP = (int) (10 * SwingUtils.getScreenScaleRatio());

    private static final int PLAY_PAUSE_RADIUS = (int) (70 * SwingUtils.getScreenScaleRatio());

    private final static Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private static final Color PLAY_PAUSE_BACKGROUND = new Color(200, 200, 200);

    private final SwingButton playPauseButton;
    private final SwingButton skipBackwardButton;
    private final SwingButton skipForwardButton;

    private final Icon playIcon;
    private final Icon pauseIcon;

    public PlayerControlsComponent(
            SwingButton playPauseButton,
            SwingButton skipBackwardButton,
            SwingButton skipForwardButton,
            Icon playIcon,
            Icon pauseIcon
    ) {

        this.playPauseButton = playPauseButton;
        this.skipBackwardButton = skipBackwardButton;
        this.skipForwardButton = skipForwardButton;

        this.playIcon = playIcon;
        this.pauseIcon = pauseIcon;

        initializeComponent();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());

        SwingUtils.setHandCursor(playPauseButton, skipBackwardButton, skipForwardButton);

        setBackground(BACKGROUND_COLOR);

        SwingPanel playPauseWrapper = new SwingRoundedPanel(PLAY_PAUSE_RADIUS);
        playPauseWrapper.setBackground(PLAY_PAUSE_BACKGROUND);
        playPauseWrapper.setLayout(new GridBagLayout());
        playPauseWrapper.add(playPauseButton);

        SwingPanel buttonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, CONTROLS_BUTTONS_HGAP, CONTROLS_BUTTONS_VGAP));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(skipBackwardButton);
        buttonsPanel.add(playPauseWrapper);
        buttonsPanel.add(skipForwardButton);

        add(buttonsPanel, BorderLayout.CENTER);

        initializePlayPauseButton();
        initializeSkipBackwardButton();
        initializeSkipForwardButton();
    }

    private void initializePlayPauseButton() {
        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setBorderPainted(false);
    }

    private void initializeSkipBackwardButton() {
        skipBackwardButton.setContentAreaFilled(false);
        skipBackwardButton.setBorderPainted(false);
    }

    private void initializeSkipForwardButton() {
        skipForwardButton.setContentAreaFilled(false);
        skipForwardButton.setBorderPainted(false);
    }

    public SwingButton getPlayPauseButton() {
        return this.playPauseButton;
    }

    public SwingButton getSkipBackwardButton() {
        return this.skipBackwardButton;
    }

    public SwingButton getSkipForwardButton() {
        return this.skipForwardButton;
    }

    public void setPlayPauseState(boolean isPlaying) {
        playPauseButton.setIcon(isPlaying ? pauseIcon : playIcon);
    }
}
