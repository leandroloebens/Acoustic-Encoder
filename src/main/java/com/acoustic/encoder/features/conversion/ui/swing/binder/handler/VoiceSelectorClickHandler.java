package com.acoustic.encoder.features.conversion.ui.swing.binder.handler;

import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VoiceSelectorClickHandler implements BindingHandler {

    private final JRadioButton button;
    private final Runnable action;

    public VoiceSelectorClickHandler(JRadioButton button, Runnable action) {
        if (button == null) throw new IllegalArgumentException("button cannot be null");
        this.button = button;

        if (action == null) throw new IllegalArgumentException("action cannot be null");
        this.action = action;
    }

    @Override
    public void bind(List<Runnable> removers) {
        ActionListener listener = _ -> action.run();
        button.addActionListener(listener);
        removers.add(() -> button.removeActionListener(listener));
    }
}
