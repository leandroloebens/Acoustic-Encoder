package com.acoustic.encoder.features.player.view.swing.components.dto;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.features.player.view.PlayerControlsComponent;
import com.acoustic.encoder.features.player.view.PlayerFooterComponent;
import com.acoustic.encoder.shared.view.swing.SwingButton;
import com.acoustic.encoder.shared.view.swing.SwingLabel;
import com.acoustic.encoder.shared.view.swing.SwingVerticalScrollPane;

public record PlayerScreenComponentsWrapper(
        PlayerControlsComponent controlsComponent,
        PlayerFooterComponent footerComponent
) { }