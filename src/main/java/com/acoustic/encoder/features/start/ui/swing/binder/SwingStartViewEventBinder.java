package com.acoustic.encoder.features.start.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.ui.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

public interface SwingStartViewEventBinder {

    void bind(
            StartController controller,
            SwingFrame frame,
            StartViewSwingComponentsWrapper components,
            EventBus eventBus
    );

    void unbind();
}
