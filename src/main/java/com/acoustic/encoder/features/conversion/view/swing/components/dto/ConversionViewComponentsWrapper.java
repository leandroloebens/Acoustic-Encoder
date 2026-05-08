package com.acoustic.encoder.features.conversion.view.swing.components.dto;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.components.SwingButton;
import com.acoustic.encoder.shared.view.swing.components.SwingLabel;
import com.acoustic.encoder.shared.view.swing.components.SwingVerticalScrollPane;

public record ConversionViewComponentsWrapper(
        SwingButton converterButton,
        SwingButton saveTextButton,
        SwingButton loadTextButton,
        SwingVerticalScrollPane scrollPane,
        SwingLabel instructionLabel,
        ParameterPanel volumePanel,
        ParameterPanel octavePanel,
        ParameterPanel instrumentPanel,
        ParameterPanel bpmPanel
) { }