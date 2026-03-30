package com.acoustic.encoder.gui;

import com.acoustic.encoder.controller.AudioPlayerController;

import javax.swing.*;
import java.awt.*;

public class PlayerScreen {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static String PLAY_BUTTON_TEXT = "Play";
    private final static String PAUSE_BUTTON_TEXT = "Pause";
    private final static String REWIND_BUTTON_TEXT = "Rewind";

    private final static int BUTTON_PANEL_TGAP = 10;
    private final static int BUTTON_PANEL_LGAP = 10;
    private final static int BUTTON_PANEL_BGAP = 10;
    private final static int BUTTON_PANEL_RGAP = 10;

    private static JFrame frame;

    private final AudioPlayerController playerController;

    public PlayerScreen(AudioPlayerController playerController) {

        if (frame == null) frame = new JFrame(WINDOW_TITLE);

        if (playerController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.playerController = playerController;
    }

    public void startFrame() {

        // Sets the window to close when the user clicks the close button.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Divides the window in NORTH, SOUTH, EAST, WEST and CENTER.
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        JPanel buttonPanel = createButtonPanel();

        // Adding components to the frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        panel.setBorder(BorderFactory.createEmptyBorder(
                BUTTON_PANEL_TGAP,
                BUTTON_PANEL_LGAP,
                BUTTON_PANEL_BGAP,
                BUTTON_PANEL_RGAP
        ));

        JButton playButton = createPlayButton();
        JButton pauseButton = createPauseButton();
        JButton rewindButton = createRewindButton();

        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(rewindButton);

        return panel;
    }

    private JButton createPlayButton() {

        JButton playButton = new JButton(PLAY_BUTTON_TEXT);

        playButton.addActionListener(event -> {

            if (event.getSource() != playButton) return;

            this.playerController.handlePlayAction();
        });

        return playButton;
    }

    private JButton createPauseButton() {

        JButton pauseButton = new JButton(PAUSE_BUTTON_TEXT);

        pauseButton.addActionListener(event -> {

            if (event.getSource() != pauseButton) return;

            this.playerController.handlePauseAction();
        });

        return pauseButton;
    }

    private JButton createRewindButton() {

        JButton rewindButton = new JButton(REWIND_BUTTON_TEXT);

        rewindButton.addActionListener(event -> {

            if (event.getSource() != rewindButton) return;

            this.playerController.handleRewindAction();
        });

        return rewindButton;
    }
}
