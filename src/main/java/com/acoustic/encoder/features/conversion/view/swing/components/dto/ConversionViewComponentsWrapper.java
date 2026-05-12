package com.acoustic.encoder.features.conversion.view.swing.components.dto;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.shared.view.swing.components.SwingButton;
import com.acoustic.encoder.shared.view.swing.components.SwingLabel;
import com.acoustic.encoder.shared.view.swing.components.SwingRadioButtonGroup;
import com.acoustic.encoder.shared.view.swing.components.SwingVerticalScrollPane;

public record ConversionViewComponentsWrapper(
        SwingButton converterButton,
        SwingButton saveTextButton,
        SwingButton loadTextButton,
        SwingVerticalScrollPane scrollPane,
        SwingLabel instructionLabel,
        SwingRadioButtonGroup trackSelector,
        ParameterSliderPanel volumePanel,
        ParameterSliderPanel octavePanel,
        ParameterComboBoxPanel<Integer> instrumentPanel,
        ParameterSliderPanel bpmPanel
) { }