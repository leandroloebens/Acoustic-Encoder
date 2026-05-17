package com.acoustic.encoder.features.start.ui.swing;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.ui.StartViewManager;
import com.acoustic.encoder.features.start.event.StartScreenCloseRequestEvent;
import com.acoustic.encoder.features.start.ui.swing.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.ui.swing.binder.SwingStartViewEventBinder;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSwingStartViewManager implements StartViewManager {

    private static final String WINDOW_TITLE = "Acoustic Encoder";

    private static final int WINDOW_MIN_HEIGHT = (int) (750 * SwingUtils.getScreenScaleRatio());
    private static final int WINDOW_MIN_WIDTH = (int) (850 * SwingUtils.getScreenScaleRatio());

    private static final int FRAME_EXIT_OPERATION = JFrame.DO_NOTHING_ON_CLOSE;

    private final SwingStartViewFrameAssembler assembler;

    private final SwingStartViewEventBinder binder;

    private final EventBus eventBus;

    private SwingFrame frame;

    public DefaultSwingStartViewManager(
            SwingStartViewFrameAssembler assembler,
            SwingStartViewEventBinder binder,
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
    public void assemble(StartController controller) {

        if (frame != null) return;

        this.frame = this.assembler.assembleFrame(
                WINDOW_TITLE,
                new Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT),
                FRAME_EXIT_OPERATION
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new StartScreenCloseRequestEvent());
            }
        });

        binder.bind(controller, frame, assembler.getComponents(), eventBus);
    }

    @Override
    public void show() {
        this.frame.setVisible(true);
    }

    @Override
    public void hide() {
        this.frame.setVisible(false);
    }

    @Override
    public void dispose() {
        binder.unbind();
        frame.dispose();
//        eventBus.publish(new StartScreenClosedEvent());
    }

}
