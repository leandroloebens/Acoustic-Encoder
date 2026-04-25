package com.acoustic.encoder.features.player.view.swing.components;

import com.acoustic.encoder.features.player.view.swing.SwingPlayerEventHandler;
import com.acoustic.encoder.shared.view.swing.SwingFrame;

public interface SwingPlayerScreenAssembler {
    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingPlayerEventHandler handler
    );
}
