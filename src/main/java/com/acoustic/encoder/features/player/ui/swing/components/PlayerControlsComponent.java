package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingRoundedPanel;
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
        setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        SwingUtils.setHandCursor(playPauseButton, rewindButton, forwardButton);

        //setBackground(Color.RED);
        setBackground(new Color(18,18,18));

        JPanel playPauseWrapper = new SwingRoundedPanel(70);
        playPauseWrapper.setBackground(new Color(200, 200, 200));
        playPauseWrapper.setLayout(new GridBagLayout());
        //playPauseWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        playPauseWrapper.add(playPauseButton);

        playPauseButton.setContentAreaFilled(false);
        playPauseButton.setBorderPainted(false);

        forwardButton.setContentAreaFilled(false);
        forwardButton.setBorderPainted(false);

        rewindButton.setContentAreaFilled(false);
        rewindButton.setBorderPainted(false);

        add(rewindButton);
        add(playPauseWrapper);
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
        playPauseButton.setIcon(!isPlaying ? playIcon : pauseIcon);
    }
}
