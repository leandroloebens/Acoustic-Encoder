package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingRoundedPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerControlsComponent extends JPanel {

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
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        SwingUtils.setHandCursor(playPauseButton, skipBackwardButton, skipForwardButton);

        //setBackground(Color.RED);
        setBackground(new Color(18,18,18));

        JPanel playPauseWrapper = new SwingRoundedPanel(70);
        playPauseWrapper.setBackground(new Color(200, 200, 200));
        playPauseWrapper.setLayout(new GridBagLayout());
        //playPauseWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        playPauseWrapper.add(playPauseButton);

        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setBorderPainted(false);

        skipForwardButton.setContentAreaFilled(false);
        skipForwardButton.setBorderPainted(false);

        skipBackwardButton.setContentAreaFilled(false);
        skipBackwardButton.setBorderPainted(false);

        add(skipBackwardButton);
        add(playPauseWrapper);
        add(skipForwardButton);
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
