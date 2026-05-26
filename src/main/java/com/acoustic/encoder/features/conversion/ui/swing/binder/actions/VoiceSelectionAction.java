package com.acoustic.encoder.features.conversion.ui.swing.binder.actions;

import com.acoustic.encoder.features.conversion.ui.swing.binder.validator.InputValidator;
import com.acoustic.encoder.features.conversion.ui.swing.binder.validator.ValidationResult;
import com.acoustic.encoder.features.conversion.ui.swing.components.VoiceSelectorPanel;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;

public class VoiceSelectionAction implements Runnable {

    private final JRadioButton button;
    private final VoiceSelectorPanel panel;
    private final SwingFrame frame;
    private final SwingConversionViewSynchronizer synchronizer;
    private final InputValidator instrumentValidator;

    public VoiceSelectionAction(
            JRadioButton button,
            VoiceSelectorPanel panel,
            SwingFrame frame,
            SwingConversionViewSynchronizer synchronizer,
            InputValidator instrumentValidator
    ) {
        if (button == null) throw new IllegalArgumentException("Radio button cannot be null");
        this.button = button;

        if  (panel == null) throw new IllegalArgumentException("VoiceSelectorPanel cannot be null");
        this.panel = panel;

        if  (frame == null) throw new IllegalArgumentException("Frame cannot be null");
        this.frame = frame;

        if (synchronizer == null) throw new IllegalArgumentException("SwingConversionViewSynchronizer cannot be null");
        this.synchronizer = synchronizer;

        if  (instrumentValidator == null) throw new IllegalArgumentException("InstrumentValidator cannot be null");
        this.instrumentValidator = instrumentValidator;
    }

    @Override
    public void run() {
        panel.getPreviousButton().setSelected(true);
        button.setSelected(false);

        ValidationResult result = instrumentValidator.validate();
        if (result.valid()) {
            panel.getPreviousButton().setSelected(false);
            button.setSelected(true);
            panel.setPreviousButton(button);

            synchronizer.syncVoiceSelector();
        }
        else SwingUtils.showWarningMessage(frame, result.feedbackMessage());
    }
}
