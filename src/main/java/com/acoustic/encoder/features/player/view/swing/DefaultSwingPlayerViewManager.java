package com.acoustic.encoder.features.player.view.swing;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.view.PlayerViewManager;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class DefaultSwingPlayerViewManager implements PlayerViewManager {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingPlayerViewAssembler assembler;

    private final EventBus eventBus;

    private SwingFrame frame;

    public DefaultSwingPlayerViewManager(SwingPlayerViewAssembler assembler, EventBus eventBus) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

    }

    @Override
    public void startFrame(AudioPlayerController controller) {
        frame = assembler.assembleFrame(
                WINDOW_TITLE,
                WINDOW_WIDTH,
                WINDOW_HEIGHT,
                FRAME_EXIT_OPERATION,
                new PlayerActionHandler(controller)
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new PlayerClosedEvent());
            }
        });

        showFrame();
    }

    @Override
    public void showFrame() { frame.setVisible(true); }

    @Override
    public void hideFrame() { frame.setVisible(false); }

    // void destroyFrame();

    private class PlayerActionHandler implements SwingPlayerViewActionHandler {

        private static final String ONSAVE_FILE_EXTENSION_FILTER = "mid";
        private static final String ONSAVE_FILTER_DESCRIPTION = "MID Files (*.mid)";
        private static final String ONSAVE_DIALOG_TITLE = "Save as";

        private final AudioPlayerController controller;

        public PlayerActionHandler(AudioPlayerController controller) {
            this.controller = controller;
        }

        @Override
        public void onPlay() {
            controller.handlePlayAction();
        }

        @Override
        public void onPause() {
            controller.handlePauseAction();
        }

        @Override
        public void onRewind() {
            controller.handleRewindAction();
        }

        @Override
        public void onSave() {
            File fileToSave = SwingUtils.getFileFromChooser(
                    SwingUtils.SAVE_FILE_OPERATION,
                    frame,
                    ONSAVE_FILE_EXTENSION_FILTER,
                    ONSAVE_FILTER_DESCRIPTION,
                    ONSAVE_DIALOG_TITLE
            );

            if (fileToSave != null) {

                try {
                    controller.handleSaveAction(fileToSave);
                } catch (MusicExportException ex) {
                    throw new RuntimeException(ex);
                }

            }

        }
    }
}
