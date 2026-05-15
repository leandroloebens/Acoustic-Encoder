package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.event.ConversionScreenClosedEvent;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.features.conversion.view.swing.frame.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.view.swing.frame.binder.SwingConversionViewFrameBinder;
import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSwingConversionViewManager implements ConversionViewManager {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int WINDOW_MIN_HEIGHT = 750;
    private final static int WINDOW_MIN_WIDTH = 850;
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingConversionViewFrameAssembler assembler;

    private final SwingConversionViewFrameBinder binder;

    private final EventBus eventBus;

    private SwingFrame frame;

    public DefaultSwingConversionViewManager(
            SwingConversionViewFrameAssembler assembler,
            SwingConversionViewFrameBinder binder,
            EventBus eventBus
    ) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

        if (binder == null) throw new IllegalArgumentException("Binder cannot be null!");
        this.binder = binder;

    }

    @Override
    public void assemble(ConversionController conversionController) {
        Dimension windowInitialSize =
            new Dimension(
                (int)(WINDOW_MIN_WIDTH * SwingUtils.getScreenScaleRatio()),
                (int)(WINDOW_MIN_HEIGHT * SwingUtils.getScreenScaleRatio())
            );

        frame = assembler.assembleFrame(
            WINDOW_TITLE,
            windowInitialSize,
            FRAME_EXIT_OPERATION
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new ConversionScreenClosedEvent());
            }
        });

        binder.bind(conversionController, frame, assembler.getComponents());

    }

    @Override
    public void show() { frame.setVisible(true); }

    @Override
    public void hide() { frame.setVisible(false); }

    @Override
    public void dispose() { frame.dispose(); }

}