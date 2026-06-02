package com.acoustic.encoder.features.conversion.ui.swing.synchronizer;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.features.conversion.dto.MusicParametersState;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.dto.VoiceParametersState;
import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;

import java.util.Objects;

public class DefaultSwingConversionViewSynchronizer implements SwingConversionViewSynchronizer {

    private MusicParametersState parameters = new MusicParametersState();

    private final ConversionViewSwingComponentsWrapper comps;

    private boolean isSyncEnabled = false;

    public DefaultSwingConversionViewSynchronizer(
            ConversionViewSwingComponentsWrapper components
    ) {
        this.comps = Objects.requireNonNull(components, "Conversion components cannot be null");
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

        this.parameters = MusicParametersState.fromMusicProject(project);

        syncInitialVoicesValues();

        comps.mainTextAreaPanel().setText(project.text());
    }

    @Override
    public void syncBpm() {
        if (!isSyncEnabled) return;
        parameters.setBpm(new Bpm(comps.bpmPanel().getValue()));
    }

    @Override
    public void syncVoiceVolume() {
        if (!isSyncEnabled) return;
        parameters.setVoiceVolume(
                comps.voiceSelector().getSelectedIndex(),
                new Volume(comps.volumePanel().getValue())
        );
    }

    @Override
    public void syncVoiceOctave() {
        if (!isSyncEnabled) return;
        parameters.setVoiceOctave(
                comps.voiceSelector().getSelectedIndex(),
                new Octave(comps.octavePanel().getValue())
        );
    }

    @Override
    public void syncVoiceInstrument() {
        if (!isSyncEnabled) return;
        parameters.setVoiceInstrument(
                comps.voiceSelector().getSelectedIndex(),
                comps.instrumentPanel().getSelectedItem().instrumentId()
        );
    }

    @Override
    public void syncVoiceSelector() {
        if (!isSyncEnabled) return;
        VoiceParametersState track =
                parameters.getSelectedVoice(comps.voiceSelector().getSelectedIndex());

        comps.volumePanel().setValue(track.getVolume().value());
        comps.octavePanel().setValue(track.getOctave().value());
        comps.instrumentPanel().setSelectedItemByIndex(track.getInstrument().value());
        if (!comps.instrumentPanel().isTextEditorInputValid())
            comps.instrumentPanel().resetItems();
    }

    private void syncInitialVoicesValues() {
        VoiceParametersState track = parameters.getSelectedVoice(comps.voiceSelector().getSelectedIndex());

        comps.volumePanel().setValue(track.getVolume().value());

        comps.octavePanel().setValue(track.getOctave().value());

        comps.bpmPanel().setValue(parameters.getBpm().value());

        comps.instrumentPanel().setSelectedItemByIndex(track.getInstrument().value());
        comps.instrumentPanel().setInitialItem(comps.instrumentPanel().getSelectedItem());
    }

}
