package com.acoustic.encoder.features.player.view.swing.components;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.view.PlayerScreenManager;
import com.acoustic.encoder.features.player.view.swing.SwingPlayerActionHandler;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class DefaultSwingPlayerScreenManager implements PlayerScreenManager {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingPlayerScreenAssembler assembler;

    private final EventBus eventBus;

    private SwingFrame frame;

    public DefaultSwingPlayerScreenManager(SwingPlayerScreenAssembler assembler, EventBus eventBus) {

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

    private class PlayerActionHandler implements SwingPlayerActionHandler {
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
        public void onSave(File file) {
            try {
                controller.handleSaveAction(file);
            } catch (MusicExportException ex) {
                    throw new RuntimeException(ex);
            }
        }
    }
}
