package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.shared.model.MusicModel;

public interface PlayerScreen {

    void startFrame();

    void closeFrame();

    void loadMusic(MusicModel musicModel);
}
