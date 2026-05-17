package com.acoustic.encoder.features.player.ui.swing.assembler;

import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

public interface SwingPlayerViewAssembler {
    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation
    );

    PlayerViewComponentsWrapper getComponents();
}
