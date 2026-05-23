package com.acoustic.encoder.features.conversion.ui.swing.binder.validator;

import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;

public class InstrumentInputValidator implements InputValidator {

    private final static String INVALID_INSTRUMENT_INPUT_WARNING =
            "Invalid instrument set -> Last valid instrument restored";

    private final ParameterComboBoxPanel<?> panel;

    public InstrumentInputValidator(ParameterComboBoxPanel<?> panel) {
        if (panel == null) throw new IllegalArgumentException("Panel cannot be null");
        this.panel = panel;
    }

    @Override
    public ValidationResult validate() {
        if (panel.isTextEditorInputValid()) {
            panel.fireTextEditorActions();
            return new ValidationResult(true, "");
        }
        else return new ValidationResult(false, INVALID_INSTRUMENT_INPUT_WARNING);
    }
}
