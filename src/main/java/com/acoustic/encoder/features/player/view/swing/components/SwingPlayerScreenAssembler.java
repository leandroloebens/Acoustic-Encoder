package com.acoustic.encoder.features.player.view.swing.components;

import com.acoustic.encoder.features.player.view.swing.SwingPlayerActionHandler;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

public interface SwingPlayerScreenAssembler {
    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingPlayerActionHandler handler
    );
}
