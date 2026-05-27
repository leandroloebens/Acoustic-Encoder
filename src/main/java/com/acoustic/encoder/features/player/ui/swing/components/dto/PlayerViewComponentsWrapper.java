package com.acoustic.encoder.features.player.ui.swing.components.dto;

import com.acoustic.encoder.features.player.ui.swing.components.MusicProgressBarPanel;
import com.acoustic.encoder.features.player.ui.swing.components.PlayPauseButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;

public record PlayerViewComponentsWrapper(
        PlayPauseButton playPauseButton,
        SwingButton skipMusicForwardButton,
        SwingButton skipMusicBackwardButton,
        MusicProgressBarPanel progressBarPanel,
        SwingButton saveMusicButton
) { }