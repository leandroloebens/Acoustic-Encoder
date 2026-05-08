package com.acoustic.encoder.features.player.view.swing.components;

import com.acoustic.encoder.features.player.view.swing.SwingPlayerViewActionHandler;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerControlsComponent extends JPanel {

    private final static String PLAY_BUTTON_TEXT = "Play";
    private final static String PAUSE_BUTTON_TEXT = "Pause";
    private final static String REWIND_BUTTON_TEXT = "Rewind";

    private final JButton playButton;
    private final JButton pauseButton;
    private final JButton rewindButton;

    private SwingPlayerViewActionHandler handler;

    public PlayerControlsComponent() {
        this.playButton = new JButton(PLAY_BUTTON_TEXT);
        this.pauseButton = new JButton(PAUSE_BUTTON_TEXT);
        this.rewindButton = new JButton(REWIND_BUTTON_TEXT);

        initializeComponent();
    }

    private void initializeComponent() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        SwingUtils.setHandCursor(playButton, pauseButton, rewindButton);

        //setBackground(Color.RED);
        setBackground(Color.darkGray);

        add(playButton);
        add(pauseButton);
        add(rewindButton);
    }

    public void setEventHandler(SwingPlayerViewActionHandler handler) {
        this.handler = handler;

        registerListeners(handler);
    }

    private void registerListeners(SwingPlayerViewActionHandler handler) {
        playButton.addActionListener(e -> handler.onPlay());
        pauseButton.addActionListener(e -> handler.onPause());
        rewindButton.addActionListener(e -> handler.onRewind());
    }
}
