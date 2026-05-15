package com.acoustic.encoder.features.start.view.swing.frame.binder;

import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

import java.util.ArrayList;
import java.util.List;

public class DefaultSwingStartViewFrameBinder implements SwingStartViewFrameBinder {

    private boolean bound;

    private final List<Runnable> removers = new ArrayList<>();

    public DefaultSwingStartViewFrameBinder() {
        this.bound = false;
    }

    @Override
    public void bind(StartController controller, SwingFrame frame, StartViewSwingComponentsWrapper components) {
        if (bound) return;

        bound = true;
    }

    @Override
    public void unbind() {
        bound = false;
    }
}
