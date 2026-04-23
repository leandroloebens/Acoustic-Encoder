package com.acoustic.encoder.features.conversion.view.swing.components.dto;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.SwingButton;
import com.acoustic.encoder.shared.view.swing.SwingLabel;
import com.acoustic.encoder.shared.view.swing.SwingVerticalScrollPane;

public record ConversionScreenComponentsWrapper(
        SwingButton converterButton,
        SwingVerticalScrollPane scrollPane,
        SwingLabel instructionLabel,
        ParameterPanel volumePanel,
        ParameterPanel octavePanel,
        ParameterPanel instrumentPanel,
        ParameterPanel bpmPanel
) { }