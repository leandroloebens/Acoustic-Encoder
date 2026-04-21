package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;

import javax.swing.*;
import java.awt.*;

public class PlayerFooterComponent extends JPanel {

    private final static String SAVE_BUTTON_TEXT = "Save";

    private final JProgressBar progressBar;
    private final JButton saveButton;

    public PlayerFooterComponent(AudioPlayerController controller) {
        this.saveButton = new JButton(SAVE_BUTTON_TEXT);
        this.progressBar = new JProgressBar();

        initializeComponent();
        registerListeners(controller);
    }

    private void initializeComponent() {
        //setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

        saveButton.setCursor(handCursor);

        add(progressBar, BorderLayout.CENTER);
        add(saveButton, BorderLayout.EAST);

        updateProgress(10);
    }

    private void registerListeners(AudioPlayerController controller) {
        //saveButton.addActionListener(e -> controller.handlePlayAction());
    }

    public void updateProgress(int value) {
        progressBar.setValue(value);
    }
}
