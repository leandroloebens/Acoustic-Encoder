package com.acoustic.encoder.features.conversion.ui.swing.components.dto;

import com.acoustic.encoder.features.conversion.ui.swing.components.MainTextAreaPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.VoiceSelectorPanel;
import com.acoustic.encoder.domain.music.InstrumentOption;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;

public record ConversionViewSwingComponentsWrapper(
        SwingButton converterButton,
        SwingButton saveTextButton,
        SwingButton loadTextButton,
        SwingButton saveProjectButton,
        SwingButton openProjectButton,
        MainTextAreaPanel mainTextAreaPanel,
        VoiceSelectorPanel voiceSelector,
        ParameterSliderPanel volumePanel,
        ParameterSliderPanel octavePanel,
        ParameterComboBoxPanel<InstrumentOption> instrumentPanel,
        ParameterSliderPanel bpmPanel
) {
    public ConversionViewSwingComponentsWrapper copy() {
        return new ConversionViewSwingComponentsWrapper(
                this.converterButton(),
                this.saveTextButton(),
                this.loadTextButton(),
                this.saveProjectButton(),
                this.openProjectButton(),
                this.mainTextAreaPanel(),
                this.voiceSelector(),
                this.volumePanel(),
                this.octavePanel(),
                this.instrumentPanel(),
                this.bpmPanel()
        );
    }
}