package com.acoustic.encoder.features.start.view.swing.binder;

import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

public interface SwingStartViewEventBinder {

    void bind(
            StartController controller,
            SwingFrame frame,
            StartViewSwingComponentsWrapper components,
            EventBus eventBus
    );

    void unbind();
}
