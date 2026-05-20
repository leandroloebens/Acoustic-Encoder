package com.acoustic.encoder.features.conversion.ui.swing.synchronizer;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.features.conversion.dto.MusicParametersState;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.dto.VoiceParametersState;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;

public class DefaultSwingConversionViewSynchronizer implements SwingConversionViewSynchronizer {

    private static final String NULL_PARAMETERS_SERVICE_ERROR_MSG = "Parameters service cannot be null!";
    private static final String NULL_COMPONENTS_ERROR_MSG = "Components cannot be null!";

    private static final int INITIAL_VOICE = 0;

    private MusicParametersState parameters = new MusicParametersState();

    private final ConversionViewSwingComponentsWrapper comps;

    private final ConversionParametersService parametersService;

    private boolean isSyncEnabled = false;

    public DefaultSwingConversionViewSynchronizer(
            ConversionViewSwingComponentsWrapper components, ConversionParametersService parametersService
    ) {
        if (components == null) throw new IllegalArgumentException(NULL_COMPONENTS_ERROR_MSG);
        this.comps = components;

        if (parametersService == null) throw new IllegalArgumentException(NULL_PARAMETERS_SERVICE_ERROR_MSG);
        this.parametersService = parametersService;
    }

    @Override
    public void enableSync() { isSyncEnabled = true; }

    @Override
    public void disableSync() { isSyncEnabled = false; }

    @Override
    public MusicParametersState getParameters() { return parameters; }

    @Override
    public void syncMusicProject(MusicProject project) {
        if (!isSyncEnabled) return;

        if (project == null) return;

        this.parameters = parametersService.unwrapMusicProject(project);

        syncInitialVoicesValues();

        comps.mainTextAreaPanel().setText(project.text());
    }

    @Override
    public void syncBpm() {
        if (!isSyncEnabled) return;
        parameters.setBpm(new Bpm(comps.bpmPanel().getSlider().getValue()));
    }

    @Override
    public void syncVoiceVolume() {
        if (!isSyncEnabled) return;
        parameters.setVoiceVolume(
                comps.voiceSelector().getSelectedIndex(),
                new Volume(comps.volumePanel().getSlider().getValue())
        );
    }

    @Override
    public void syncVoiceOctave() {
        if (!isSyncEnabled) return;
        parameters.setVoiceOctave(
                comps.voiceSelector().getSelectedIndex(),
                new Octave(comps.octavePanel().getSlider().getValue())
        );
    }

    @Override
    public void syncVoiceInstrument() {
        if (!isSyncEnabled) return;
        parameters.setVoiceInstrument(
                comps.voiceSelector().getSelectedIndex(),
                new InstrumentId(comps.instrumentPanel().getSelectedItem().id())
        );
    }

    @Override
    public void syncVoiceSelector() {
        if (!isSyncEnabled) return;
        VoiceParametersState track =
                parameters.getSelectedVoice(comps.voiceSelector().getSelectedIndex());

        comps.volumePanel().getSlider().setValue(track.getVolume().value());
        comps.octavePanel().getSlider().setValue(track.getOctave().value());
        comps.instrumentPanel().setSelectedItemByIndex(track.getInstrument().value());
        comps.instrumentPanel().getComboBox().finishEditing();
    }

    private void syncInitialVoicesValues() {
        VoiceParametersState track = parameters.getSelectedVoice(comps.voiceSelector().getSelectedIndex());

        comps.volumePanel().getSlider().setValue(track.getVolume().value());

        comps.octavePanel().getSlider().setValue(track.getOctave().value());

        comps.bpmPanel().getSlider().setValue(parameters.getBpm().value());

        comps.instrumentPanel().setSelectedItemByIndex(track.getInstrument().value());
        comps.instrumentPanel().getComboBox().setInitialItem(comps.instrumentPanel().getSelectedItem());
    }

}
