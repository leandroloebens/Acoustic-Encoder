package com.acoustic.encoder.features.player.ui;

import com.acoustic.encoder.domain.music.MusicModel;

public interface PlayerScreen {

    void startFrame();

    void closeFrame();

    void loadMusic(MusicModel musicModel);
}
