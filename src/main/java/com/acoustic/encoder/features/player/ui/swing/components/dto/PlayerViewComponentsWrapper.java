package com.acoustic.encoder.features.player.ui.swing.components.dto;

import com.acoustic.encoder.features.player.ui.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.ui.swing.components.PlayerFooterComponent;

public record PlayerViewComponentsWrapper(
        PlayerControlsComponent controlsComponent,
        PlayerFooterComponent footerComponent
) { }