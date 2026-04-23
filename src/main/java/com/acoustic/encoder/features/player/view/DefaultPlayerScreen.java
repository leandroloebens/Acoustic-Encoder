package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.model.MusicModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class DefaultPlayerScreen implements PlayerScreen {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static String PLAY_BUTTON_TEXT = "Play";
    private final static String PAUSE_BUTTON_TEXT = "Pause";
    private final static String REWIND_BUTTON_TEXT = "Rewind";
    private final static String SAVE_BUTTON_TEXT = "Save";

    private final static int BUTTON_PANEL_TGAP = 10;
    private final static int BUTTON_PANEL_LGAP = 10;
    private final static int BUTTON_PANEL_BGAP = 10;
    private final static int BUTTON_PANEL_RGAP = 10;

    private final JFrame frame;

    private final AudioPlayerController playerController;

    private final EventBus eventBus;

    public DefaultPlayerScreen(AudioPlayerController playerController, EventBus eventBus) {

        if (playerController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.playerController = playerController;

        this.eventBus = eventBus;

        this.frame = new JFrame(WINDOW_TITLE);
        this.initializeFrame();

    }

    public void initializeFrame() {

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

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new PlayerClosedEvent());
            }
        });

    }

    public void startFrame() {
        this.frame.setVisible(true);
    }

    public void closeFrame() {
        this.frame.setVisible(false);
    }

    public void loadMusic(MusicModel musicModel) {

        this.playerController.handleLoadAction(musicModel);
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
        JButton saveButton = createSaveButton();

        panel.add(playButton);
        panel.add(pauseButton);
        panel.add(rewindButton);
        panel.add(saveButton);

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

    private JButton createSaveButton() {

        JButton saveButton = new JButton(SAVE_BUTTON_TEXT);

        saveButton.addActionListener(event -> {

            if (event.getSource() != saveButton) return;

            try {
                this.playerController.handleSaveAction(chooseFile());
                JOptionPane.showMessageDialog(frame, "Saved!");
            } catch (MusicExportException e) {
                JOptionPane.showMessageDialog(frame, "Error while exporting!");
            }
        });

        return saveButton;
    }

    private static File chooseFile() {

        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Save MIDI file");

        chooser.setSelectedFile(new File("music.mid"));

        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            if (!file.getName().endsWith(".mid")) {
                file = new File(file.getAbsolutePath() + ".mid");
            }
            return file;
        }

        return null;
    }
}
