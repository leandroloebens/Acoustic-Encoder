package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.event.ConversionScreenClosedEvent;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.features.conversion.view.swing.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.view.swing.binder.SwingConversionViewEventBinder;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSwingConversionViewManager implements ConversionViewManager {

    private final static String WINDOW_TITLE = "Text To Sound";
    private final static int WINDOW_MIN_HEIGHT = (int) (750 * SwingUtils.getScreenScaleRatio());
    private final static int WINDOW_MIN_WIDTH = (int) (850 * SwingUtils.getScreenScaleRatio());
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingConversionViewFrameAssembler assembler;

    private final SwingConversionViewEventBinder binder;

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

        binder.bind(conversionController, frame, assembler.getComponents());

    }

    @Override
    public void show() { frame.setVisible(true); }

    @Override
    public void hide() { frame.setVisible(false); }

    @Override
    public void dispose() {
        binder.unbind();
        frame.dispose();
    }

}