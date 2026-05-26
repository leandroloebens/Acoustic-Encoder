package com.acoustic.encoder.infrastructure.ui_shared.swing.handler;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class FrameWindowBindingHandler implements BindingHandler {

    private final SwingFrame frame;
    private final Runnable action;

    public FrameWindowBindingHandler(SwingFrame frame, Runnable action) {
        if  (frame == null) throw new IllegalArgumentException("Frame may not be null");
        this.frame = frame;

        if (action == null) throw new IllegalArgumentException("Action may not be null");
        this.action = action;
    }

    @Override
    public void bind(List<Runnable> removers) {
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                action.run();
            }
        };

        frame.addWindowListener(listener);
        removers.add(() -> frame.removeWindowListener(listener));
    }

}
