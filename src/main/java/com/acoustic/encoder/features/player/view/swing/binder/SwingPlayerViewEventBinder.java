package com.acoustic.encoder.features.player.view.swing.binder;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.view.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

public interface SwingPlayerViewEventBinder {
    void bind(AudioPlayerController controller, SwingFrame frame, PlayerViewComponentsWrapper components);

    void unbind();
}
