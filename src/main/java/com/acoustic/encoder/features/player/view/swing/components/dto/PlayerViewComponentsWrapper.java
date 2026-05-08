package com.acoustic.encoder.features.player.view.swing.components.dto;

import com.acoustic.encoder.features.player.view.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.view.swing.components.PlayerFooterComponent;

public record PlayerViewComponentsWrapper(
        PlayerControlsComponent controlsComponent,
        PlayerFooterComponent footerComponent
) { }