package com.acoustic.encoder.features.player.ui;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;

public interface PlayerViewManagerFactory {
    PlayerViewManager createViewManager(AudioPlayerController controller);
}
