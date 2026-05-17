package com.acoustic.encoder.features.player.ui.swing.binder;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;


public interface SwingPlayerViewEventBinder {
    void bind(AudioPlayerController controller, SwingFrame frame, PlayerViewComponentsWrapper components);

    void unbind();
}
