package com.acoustic.encoder.features.player.ui.swing.synchronizer;

import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;

import java.util.function.LongSupplier;

@FunctionalInterface
public interface SwingPlayerViewSynchronizerFactory {

    SwingPlayerViewSynchronizer createSynchronizer(
            PlayerViewComponentsWrapper components,
            LongSupplier microsecPositionSupplier,
            LongSupplier microsecDurationSupplier
    );
}
