package com.acoustic.encoder.features.player.ui.swing.binder.handler;

import com.acoustic.encoder.features.player.ui.swing.components.MusicProgressBarPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;

import javax.swing.event.ChangeListener;
import java.util.List;
import java.util.Objects;

public class ProgressBarManualChangeBindingHandler implements BindingHandler {

    private final MusicProgressBarPanel progressPanel;
    private final Runnable action;

    public  ProgressBarManualChangeBindingHandler(MusicProgressBarPanel progressPanel, Runnable action) {
        this.progressPanel = Objects.requireNonNull(progressPanel, "progressPanel cannot be null");
        this.action = Objects.requireNonNull(action, "action cannot be null");
    }

    @Override
    public void bind(List<Runnable> removers) {
        ChangeListener changeListener = _ -> action.run();
        progressPanel.addChangeListener(changeListener);
        removers.add(() -> progressPanel.removeChangeListener(changeListener));
    }
}
