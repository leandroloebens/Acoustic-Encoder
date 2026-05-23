package com.acoustic.encoder.infrastructure.ui_shared.swing.handler;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class EditorChangeBindingHandler implements BindingHandler {

    private final JTextField editor;
    private final Runnable action;

    public EditorChangeBindingHandler(JTextField editor, Runnable action) {
        if (editor == null) throw new IllegalArgumentException("Editor cannot be null");
        this.editor = editor;

        if (action == null) throw new IllegalArgumentException("Action cannot be null");
        this.action = action;
    }

    @Override
    public void bind(List<Runnable> removers) {
        ActionListener listener = _ -> action.run();
        editor.addActionListener(listener);
        removers.add(() -> editor.removeActionListener(listener));
    }

}
