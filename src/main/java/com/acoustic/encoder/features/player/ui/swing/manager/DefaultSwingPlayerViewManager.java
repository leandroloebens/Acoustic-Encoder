package com.acoustic.encoder.features.player.ui.swing.manager;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.ui.PlayerViewManager;
import com.acoustic.encoder.features.player.ui.swing.assembler.SwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.ui.swing.binder.SwingPlayerViewEventBinder;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.util.Objects;

public class DefaultSwingPlayerViewManager implements PlayerViewManager {

    private final static String WINDOW_TITLE = "Acoustic Encoder - Music Player";
    private final static int WINDOW_MIN_WIDTH = (int) (380 * SwingUtils.getScreenScaleRatio());
    private final static int WINDOW_MIN_HEIGHT = (int) (200 * SwingUtils.getScreenScaleRatio());
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingPlayerViewAssembler assembler;
    private final SwingPlayerViewEventBinder binder;

    private SwingPlayerViewSynchronizer synchronizer;
    private SwingFrame frame;

    public DefaultSwingPlayerViewManager(
            AudioPlayerController controller,
            SwingPlayerViewAssembler assembler,
            SwingPlayerViewEventBinder binder
    ) {
        Objects.requireNonNull(assembler, "Assembler cannot be null!");
        Objects.requireNonNull(binder, "Binder cannot be null!");

        this.assembler = assembler;

        this.binder = binder;

        this.frame = assemble(controller);
    }

    private SwingFrame assemble(AudioPlayerController controller) {
        if (frame != null) return this.frame;

        SwingFrame frame = assembler.assembleFrame(
                WINDOW_TITLE,
                WINDOW_MIN_WIDTH,
                WINDOW_MIN_HEIGHT,
                FRAME_EXIT_OPERATION
        );

        this.synchronizer = binder.bind(controller, frame, assembler.getComponents());

        return frame;
    }

    @Override
    public void showFrame() {
        if (frame == null) throw new IllegalStateException("Frame is not initialized!");

        synchronizer.startSync();

        frame.setVisible(true);
    }

    @Override
    public void hideFrame() {
        if (frame == null) throw new IllegalStateException("Frame is not initialized!");

        synchronizer.stopSync();

        frame.setVisible(false);
    }

    @Override
    public void disposeFrame() {
        if (frame == null) return;

        synchronizer.stopSync();
        synchronizer = null;
        binder.unbind();
        frame.dispose();
        frame = null;
    }
}
