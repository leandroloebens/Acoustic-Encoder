package com.acoustic.encoder.features.conversion.ui.swing.manager;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.event.ConversionScreenClosedEvent;
import com.acoustic.encoder.features.conversion.ui.ConversionViewManager;
import com.acoustic.encoder.features.conversion.ui.swing.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.ui.swing.binder.SwingConversionViewEventBinder;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSwingConversionViewManager implements ConversionViewManager {

    private final static String WINDOW_TITLE = "Acoustic Encoder - Text 2 Sound";
    private final static int WINDOW_MIN_HEIGHT = (int) (600 * SwingUtils.getScreenScaleRatio());
    private final static int WINDOW_MIN_WIDTH = (int) (850 * SwingUtils.getScreenScaleRatio());
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingConversionViewFrameAssembler assembler;

    private final SwingConversionViewEventBinder binder;

    private SwingConversionViewSynchronizer synchronizer;

    private final EventBus eventBus;

    private SwingFrame frame;

    public DefaultSwingConversionViewManager(
            SwingConversionViewFrameAssembler assembler,
            SwingConversionViewEventBinder binder,
            EventBus eventBus
    ) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (binder == null) throw new IllegalArgumentException("Binder cannot be null!");
        this.binder = binder;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

    }

    @Override
    public void assemble(ConversionController conversionController) {

        if (frame != null) return;

        frame = assembler.assembleFrame(
                WINDOW_TITLE,
                new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT),
                FRAME_EXIT_OPERATION
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new ConversionScreenClosedEvent());
            }
        });

        synchronizer = binder.bind(conversionController, frame, assembler.getComponents());

        synchronizer.enableSync();

    }

    @Override
    public void show() {
        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> frame.requestFocusInWindow());
    }

    @Override
    public void hide() { frame.setVisible(false); }

    @Override
    public void dispose() {
        synchronizer.disableSync();
        binder.unbind();
        frame.dispose();
    }

}