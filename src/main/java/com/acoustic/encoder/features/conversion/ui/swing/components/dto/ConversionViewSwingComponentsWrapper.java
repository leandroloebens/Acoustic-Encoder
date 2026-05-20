package com.acoustic.encoder.features.conversion.ui.swing.components.dto;

import com.acoustic.encoder.features.conversion.ui.swing.components.MainTextAreaPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.VoiceSelectorPanel;
import com.acoustic.encoder.domain.music.InstrumentOption;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingVerticalScrollPane;

public record ConversionViewSwingComponentsWrapper(
        SwingButton converterButton,
        SwingButton saveTextButton,
        SwingButton loadTextButton,
        SwingButton saveProjectButton,
        SwingButton loadProjectButton,
        MainTextAreaPanel mainTextAreaPanel,
        VoiceSelectorPanel voiceSelector,
        ParameterSliderPanel volumePanel,
        ParameterSliderPanel octavePanel,
        ParameterComboBoxPanel<InstrumentOption> instrumentPanel,
        ParameterSliderPanel bpmPanel
) { }