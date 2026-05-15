package com.acoustic.encoder.features.start.ui.swing.components.dto;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;

public record StartViewSwingComponentsWrapper(
        SwingLabel titleLabel,
        SwingButton openProjectButton,
        SwingButton newProjectButton
) { }
