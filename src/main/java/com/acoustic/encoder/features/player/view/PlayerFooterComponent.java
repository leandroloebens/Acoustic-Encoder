package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.view.swing.SwingPlayerActionHandler;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlayerFooterComponent extends JPanel {

    private final static String SAVE_BUTTON_TEXT = "Save";

    private final JProgressBar progressBar;
    private final JButton saveButton;

    private SwingPlayerActionHandler handler;

    public PlayerFooterComponent() {
        this.saveButton = new JButton(SAVE_BUTTON_TEXT);
        this.progressBar = new JProgressBar();

        initializeComponent();
    }

    private void initializeComponent() {
        //setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        Utils.setHandCursor(saveButton);

        //setBackground(Color.BLUE);
        setBackground(Color.darkGray);

        add(progressBar, BorderLayout.CENTER);
        add(saveButton, BorderLayout.EAST);

        updateProgress(10);
    }

    public void setEventHandler(SwingPlayerActionHandler handler) {
        this.handler = handler;

        registerListeners(handler);
    }

    private void registerListeners(SwingPlayerActionHandler handler) {
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

                handler.onSave(fileToSave);
            }
        });
    }

    public void updateProgress(int value) {
        progressBar.setValue(value);
    }
}
