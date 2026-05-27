package com.acoustic.encoder.features.conversion.ui.swing.binder.handler;

import com.acoustic.encoder.features.conversion.ui.swing.binder.action.ChangeParameterComboBoxAction;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.EditorChangeBindingHandler;


import java.util.List;

public class ParameterComboBoxChangeBindingHandler implements BindingHandler {

    private final SwingFrame frame;
    private final ParameterComboBoxPanel<?> panel;
    private final String warningMessage;
    private final Runnable parameterSyncAction;

    public ParameterComboBoxChangeBindingHandler(
            SwingFrame frame,
            ParameterComboBoxPanel<?> panel,
            Runnable parameterSyncAction,
            String warningMessage
    ) {
        if (frame == null) throw new IllegalArgumentException("Frame cannot be null");
        this.frame = frame;

        if (panel == null) throw new IllegalArgumentException("Panel cannot be null");
        this.panel = panel;

        if (warningMessage == null) throw new IllegalArgumentException("Warning message cannot be null");
        this.warningMessage = warningMessage;

        if (parameterSyncAction == null) throw new IllegalArgumentException("Parameter sync action cannot be null");
        this.parameterSyncAction = parameterSyncAction;
    }

    @Override
    public void bind(List<Runnable> removers) {
        ChangeParameterComboBoxAction action =
                new ChangeParameterComboBoxAction(panel, frame, parameterSyncAction, warningMessage);

        EditorChangeBindingHandler handler = new EditorChangeBindingHandler(panel.getTextEditor(), action);
        handler.bind(removers);
    }

}
