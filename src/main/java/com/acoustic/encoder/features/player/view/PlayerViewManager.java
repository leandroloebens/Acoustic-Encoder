package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;

public interface PlayerViewManager {

    void startFrame(AudioPlayerController controller);

    void showFrame();

    void hideFrame();

    // void destroyFrame();

}
