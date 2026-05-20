package com.acoustic.encoder.features.conversion.ui.swing.synchronizer;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.features.conversion.dto.MusicParametersState;
import com.acoustic.encoder.features.conversion.dto.MusicProject;

public interface SwingConversionViewSynchronizer {

    void enableSync();

    void disableSync();

    MusicParametersState getParameters();

    void syncMusicProject(MusicProject project);

    void syncVoiceVolume();

    void syncVoiceOctave();

    void syncVoiceInstrument();

    void syncBpm();

    void syncVoiceSelector();

}
