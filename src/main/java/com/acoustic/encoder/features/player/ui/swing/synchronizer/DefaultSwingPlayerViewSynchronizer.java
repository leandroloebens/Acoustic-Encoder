package com.acoustic.encoder.features.player.ui.swing.synchronizer;

import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;

import javax.swing.*;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;

public class DefaultSwingPlayerViewSynchronizer implements SwingPlayerViewSynchronizer {

    private static final int SYNC_MILLISEC_INTERVAL = 100;

    private final Timer syncTimer;
    private final PlayerViewComponentsWrapper components;

    private final LongSupplier microsecPositionSupplier;
    private final LongSupplier microsecDurationSupplier;

    private final BooleanSupplier playingStateSupplier;

    private boolean isSyncing = false;
    private boolean isUpdatingProgrammaticaly = false;

    public DefaultSwingPlayerViewSynchronizer(
            PlayerViewComponentsWrapper components,
            LongSupplier microsecPositionSupplier,
            LongSupplier microsecDurationSupplier,
            BooleanSupplier playingStateSupplier
    ) {
        Objects.requireNonNull(components, "Components cannot be null!");
        Objects.requireNonNull(microsecPositionSupplier, "MicrosecPositionSupplier cannot be null!");
        Objects.requireNonNull(microsecDurationSupplier, "MicrosecDurationSupplier cannot be null!");

        this.components = components;
        this.microsecPositionSupplier = microsecPositionSupplier;
        this.microsecDurationSupplier = microsecDurationSupplier;

        this.playingStateSupplier = playingStateSupplier;

        this.syncTimer = new Timer(SYNC_MILLISEC_INTERVAL, e -> syncState());
    }

    @Override
    public void startSync() {
        if (isSyncing) {
            throw new IllegalStateException("Error in startSync: sync is already started!");
        }

        syncTimer.start();
        isSyncing = true;
    }

    @Override
    public void stopSync() {
        if (!isSyncing) {
            throw new IllegalStateException("Error in stopSync: sync is already stopped!");
        }

        syncTimer.stop();
        isSyncing = false;
    }

    @Override
    public boolean isUpdatingProgrammaticaly() {
        return isUpdatingProgrammaticaly;
    }

    private void syncState() {
        if (!isSyncing) return;

        syncPlaybackSeekBar();
        syncPlayPauseButton();
    }

    private void syncPlaybackSeekBar() {

        SwingSeekBar playbackSeekBar = components.footerComponent().getPlaybackSeekBar();

        if (playbackSeekBar.getValueIsAdjusting()) {
            return;
        }

        this.isUpdatingProgrammaticaly = true;
        try {
            int newPosition = (int) Math.round(
                    (double) playbackSeekBar.getMaximum()
                            * microsecPositionSupplier.getAsLong()
                            / microsecDurationSupplier.getAsLong()
            );
            components.footerComponent().getPlaybackSeekBar().setValue(newPosition);
        }
        finally {
            this.isUpdatingProgrammaticaly = false;
        }

    }

    private void syncPlayPauseButton() {
        boolean isPlaying = playingStateSupplier.getAsBoolean();
        components.controlsComponent().setPlayPauseState(isPlaying);
    }
}
