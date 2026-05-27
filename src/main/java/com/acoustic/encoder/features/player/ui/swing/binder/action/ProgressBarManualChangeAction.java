package com.acoustic.encoder.features.player.ui.swing.binder.action;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.ui.swing.components.MusicProgressBarPanel;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizer;

import java.util.Objects;

public class ProgressBarManualChangeAction implements Runnable {

    private final MusicProgressBarPanel progressPanel;
    private final AudioPlayerController controller;
    private final SwingPlayerViewSynchronizer synchronizer;

    public ProgressBarManualChangeAction(
            MusicProgressBarPanel panel,
            AudioPlayerController controller,
            SwingPlayerViewSynchronizer synchronizer
    ) {
        this.progressPanel = Objects.requireNonNull(panel, "Progress panel cannot be null!");
        this.controller = Objects.requireNonNull(controller, "Audio player controller cannot be null!");
        this.synchronizer =
                Objects.requireNonNull(synchronizer, "SwingPlayerViewSynchronizer cannot be null!");
    }

    @Override
    public void run() {
        if (synchronizer.isUpdatingProgrammatically()) return;

        if (progressPanel.isAvailableForManualChange()) {
            progressPanel.transferFocus();

            controller.handleSeekAction(
                    progressPanel.getMicroSecondsPosition(controller.getMicrosecDuration())
            );
        }
    }
}
