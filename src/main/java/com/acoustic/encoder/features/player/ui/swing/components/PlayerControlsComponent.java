package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerControlsComponent extends JPanel {

//    private final static String PLAY_BUTTON_TEXT = "Play";
//    private final static String PAUSE_BUTTON_TEXT = "Pause";
//    private final static String REWIND_BUTTON_TEXT = "Rewind";

//    private final JButton playButton;
//    private final JButton pauseButton;
//    private final JButton rewindButton;

    private final SwingButton playPauseButton;
    private final SwingButton rewindButton;

    public PlayerControlsComponent(
            SwingButton playPauseButton,
            SwingButton rewindButton
    ) {
//        this.playPauseButton = new JButton(PLAY_BUTTON_TEXT);
//        this.pauseButton = new JButton(PAUSE_BUTTON_TEXT);
//        this.rewindButton = new JButton(REWIND_BUTTON_TEXT);

        this.playPauseButton = playPauseButton;
        this.rewindButton = rewindButton;

        initializeComponent();
    }

    private void initializeComponent() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        SwingUtils.setHandCursor(playPauseButton, rewindButton);

        //setBackground(Color.RED);
        setBackground(Color.darkGray);

        add(playPauseButton);
//        add(pauseButton);
        add(rewindButton);
    }

    public SwingButton getPlayPauseButton() {
        return this.playPauseButton;
    }

//    public JButton getPauseButton() {
//        return this.pauseButton;
//    }

    public SwingButton getRewindButton() {
        return this.rewindButton;
    }
}
