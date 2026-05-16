package com.acoustic.encoder.features.start.view.swing.components.dto;

import com.acoustic.encoder.shared.view.swing.components.SwingButton;
import com.acoustic.encoder.shared.view.swing.components.SwingLabel;

public record StartViewSwingComponentsWrapper(
        SwingLabel titleLabel,
        SwingButton openProjectButton,
        SwingButton newProjectButton
) { }
