package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;

import javax.swing.*;
import java.awt.*;

public class PlayerControlsComponent extends JPanel {

    private final static String PLAY_BUTTON_TEXT = "Play";
    private final static String PAUSE_BUTTON_TEXT = "Pause";
    private final static String REWIND_BUTTON_TEXT = "Rewind";

    private final JButton playButton;
    private final JButton pauseButton;
    private final JButton rewindButton;

    public PlayerControlsComponent(AudioPlayerController controller) {

        this.playButton = new JButton(PLAY_BUTTON_TEXT);
        this.pauseButton = new JButton(PAUSE_BUTTON_TEXT);
        this.rewindButton = new JButton(REWIND_BUTTON_TEXT);

        initializeComponent();
        registerListeners(controller);
    }

    private void initializeComponent() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        playButton.setCursor(handCursor);
        pauseButton.setCursor(handCursor);
        rewindButton.setCursor(handCursor);

        add(playButton);
        add(pauseButton);
        add(rewindButton);
    }

    private void registerListeners(AudioPlayerController controller) {
        playButton.addActionListener(e -> controller.handlePlayAction());
        pauseButton.addActionListener(e -> controller.handlePauseAction());
        rewindButton.addActionListener(e -> controller.handleRewindAction());
    }
}
