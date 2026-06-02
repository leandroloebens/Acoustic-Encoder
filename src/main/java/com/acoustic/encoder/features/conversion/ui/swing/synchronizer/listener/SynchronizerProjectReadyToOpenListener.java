package com.acoustic.encoder.features.conversion.ui.swing.synchronizer.listener;

import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpenEvent;

import java.util.Objects;

public class SynchronizerProjectReadyToOpenListener implements EventListener<ProjectReadyToOpenEvent> {

    private final SwingConversionViewSynchronizer synchronizer;

    public SynchronizerProjectReadyToOpenListener(SwingConversionViewSynchronizer synchronizer) {
        this.synchronizer = Objects.requireNonNull(synchronizer, "Conversion synchronizer cannot be null");
    }

    @Override
    public void onEvent(ProjectReadyToOpenEvent event) {
        synchronizer.syncMusicProject(event.project());
    }

}
