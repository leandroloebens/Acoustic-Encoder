package com.acoustic.encoder.features.player.view.swing.assembler;

import com.acoustic.encoder.features.player.view.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

public interface SwingPlayerViewAssembler {
    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation
    );

    PlayerViewComponentsWrapper getComponents();
}
