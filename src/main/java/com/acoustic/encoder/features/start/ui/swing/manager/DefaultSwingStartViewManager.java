package com.acoustic.encoder.features.start.ui.swing.manager;

import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.ui.StartViewManager;
import com.acoustic.encoder.features.start.ui.swing.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.ui.swing.binder.SwingStartViewEventBinder;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingStartViewManager implements StartViewManager {

    private static final String WINDOW_TITLE = "Acoustic Encoder";

    private static final int WINDOW_MIN_HEIGHT = (int) (500 * SwingUtils.getScreenScaleRatio());
    private static final int WINDOW_MIN_WIDTH = (int) (750 * SwingUtils.getScreenScaleRatio());

    private static final int FRAME_EXIT_OPERATION = JFrame.DO_NOTHING_ON_CLOSE;

    private final SwingStartViewFrameAssembler assembler;

    private final SwingStartViewEventBinder binder;

    private SwingFrame frame;

    public DefaultSwingStartViewManager(
            StartController controller,
            SwingStartViewFrameAssembler assembler,
            SwingStartViewEventBinder binder
    ) {
        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (binder == null) throw new IllegalArgumentException("Binder cannot be null!");
        this.binder = binder;

        this.frame = assemble(controller);
    }

    private SwingFrame assemble(StartController controller) {
        if (frame != null) return this.frame;

        SwingFrame frame = this.assembler.assembleFrame(
                WINDOW_TITLE,
                new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT),
                FRAME_EXIT_OPERATION
        );

        binder.bind(controller, frame, assembler.getComponents());

        return frame;
    }

    @Override
    public void showFrame() {
        if (frame == null) throw new IllegalStateException("Frame is not initialized!");

        this.frame.setVisible(true);
    }

    @Override
    public void hideFrame() {
        if (frame == null) throw new IllegalStateException("Frame is not initialized!");

        this.frame.setVisible(false);
    }

    @Override
    public void disposeFrame() {
        if (frame == null) return;

        binder.unbind();
        frame.dispose();
        frame = null;
    }

}
