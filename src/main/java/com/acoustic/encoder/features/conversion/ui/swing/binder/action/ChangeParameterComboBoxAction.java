package com.acoustic.encoder.features.conversion.ui.swing.binder.action;

import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingMessageUtils;

public class ChangeParameterComboBoxAction implements Runnable {

    private final ParameterComboBoxPanel<?> panel;
    private final SwingFrame frame;
    private final String warningMessage;
    private final Runnable parameterSyncAction;

    public ChangeParameterComboBoxAction(
            ParameterComboBoxPanel<?> panel,
            SwingFrame frame,
            Runnable action,
            String warningMessage
    ) {
        if (frame == null) throw new IllegalArgumentException("Frame cannot be null");
        this.frame = frame;

        if (panel == null) throw new IllegalArgumentException("Panel cannot be null");
        this.panel = panel;

        if (warningMessage == null) throw new IllegalArgumentException("Warning message cannot be null");
        this.warningMessage = warningMessage;

        if  (action == null) throw new IllegalArgumentException("Action cannot be null");
        this.parameterSyncAction = action;
    }

    @Override
    public void run() {
        if (!frame.isVisible()) return;

        if (panel.isTextEditorInputValid())
            parameterSyncAction.run();
        else SwingMessageUtils.showWarningMessage(frame, warningMessage);
    }
}
