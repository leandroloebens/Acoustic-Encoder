package com.acoustic.encoder.features.start.view.swing.frame.binder;

import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

public interface SwingStartViewFrameBinder {

    void bind(StartController controller, SwingFrame frame, StartViewSwingComponentsWrapper components);

    void unbind();
}
