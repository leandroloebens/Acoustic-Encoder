package com.acoustic.encoder.features.player.view.swing;

import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

public interface SwingPlayerViewAssembler {
    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingPlayerViewActionHandler handler
    );
}
