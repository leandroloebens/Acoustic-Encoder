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
    private final SwingButton rewindButton;
    private final SwingButton forwardButton;

    private final Icon playIcon;
    private final Icon pauseIcon;

    public PlayerControlsComponent(
            SwingButton playPauseButton,
            SwingButton rewindButton,
            SwingButton forwardButton,
            Icon playIcon,
            Icon pauseIcon
    ) {

        this.playPauseButton = playPauseButton;
        this.rewindButton = rewindButton;
        this.forwardButton = forwardButton;

        this.playIcon = playIcon;
        this.pauseIcon = pauseIcon;

        initializeComponent();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());

        SwingUtils.setHandCursor(playPauseButton, rewindButton, forwardButton);

        setBackground(BACKGROUND_COLOR);

        SwingPanel playPauseWrapper = new SwingRoundedPanel(PLAY_PAUSE_RADIUS);
        playPauseWrapper.setBackground(PLAY_PAUSE_BACKGROUND);
        playPauseWrapper.setLayout(new GridBagLayout());
        playPauseWrapper.add(playPauseButton);

        SwingPanel buttonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, CONTROLS_BUTTONS_HGAP, CONTROLS_BUTTONS_VGAP));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(rewindButton);
        buttonsPanel.add(playPauseWrapper);
        buttonsPanel.add(forwardButton);

        add(buttonsPanel, BorderLayout.CENTER);

        initializePlayPauseButton();
        initializeRewindButton();
        initializeForwardButton();
    }

    private void initializePlayPauseButton() {
        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setBorderPainted(false);
    }

    private void initializeRewindButton() {
        rewindButton.setContentAreaFilled(false);
        rewindButton.setBorderPainted(false);
    }

    private void initializeForwardButton() {
        forwardButton.setContentAreaFilled(false);
        forwardButton.setBorderPainted(false);
    }

    public SwingButton getPlayPauseButton() {
        return this.playPauseButton;
    }

    public SwingButton getRewindButton() {
        return this.rewindButton;
    }

    public SwingButton getForwardButton() {
        return this.forwardButton;
    }

    public void setPlayPauseState(boolean isPlaying) {
        playPauseButton.setIcon(isPlaying ? pauseIcon : playIcon);
    }
}
