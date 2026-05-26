package com.acoustic.encoder.features.conversion.ui.swing.manager;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.ui.ConversionViewManager;
import com.acoustic.encoder.features.conversion.ui.swing.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.ui.swing.binder.SwingConversionViewEventBinder;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingConversionViewManager implements ConversionViewManager {

    private final static String WINDOW_TITLE = "Acoustic Encoder - Text 2 Sound";
    private final static int WINDOW_MIN_HEIGHT = (int) (600 * SwingUtils.getScreenScaleRatio());
    private final static int WINDOW_MIN_WIDTH = (int) (850 * SwingUtils.getScreenScaleRatio());
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingConversionViewFrameAssembler assembler;

    private final SwingConversionViewEventBinder binder;

    private SwingConversionViewSynchronizer synchronizer;

    private SwingFrame frame;

    public DefaultSwingConversionViewManager(
            ConversionController controller,
            SwingConversionViewFrameAssembler assembler,
            SwingConversionViewEventBinder binder
    ) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (binder == null) throw new IllegalArgumentException("Binder cannot be null!");
        this.binder = binder;

        this.frame = assemble(controller);

    }

    private SwingFrame assemble(ConversionController conversionController) {

        if (frame != null) return this.frame;

        SwingFrame frame = assembler.assembleFrame(
                WINDOW_TITLE,
                new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT),
                FRAME_EXIT_OPERATION
        );

        synchronizer = binder.bind(conversionController, frame, assembler.getComponents());

        synchronizer.enableSync();

        return frame;
    }

    @Override
    public void show() {
        if (frame == null) throw new IllegalStateException("Frame is not initialized!");

        synchronizer.enableSync();

        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> frame.requestFocusInWindow());
    }

    @Override
    public void hide() {
        if (frame == null) throw new IllegalStateException("Frame is not initialized!");

        synchronizer.disableSync();

        frame.setVisible(false);
    }

    @Override
    public void dispose() {
        if (frame == null) return;

        synchronizer.disableSync();
        synchronizer = null;
        binder.unbind();
        frame.dispose();
        frame = null;
    }

}