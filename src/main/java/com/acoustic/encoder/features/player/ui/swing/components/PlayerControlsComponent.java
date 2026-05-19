package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerControlsComponent extends JPanel {

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
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        SwingUtils.setHandCursor(playPauseButton, rewindButton, forwardButton);

        //setBackground(Color.RED);
        setBackground(Color.darkGray);

        add(rewindButton);
        add(playPauseButton);
        add(forwardButton);
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
        playPauseButton.setText(isPlaying ? "Play" : "Pause");
        playPauseButton.setIcon(isPlaying ? playIcon : pauseIcon);
    }
}
