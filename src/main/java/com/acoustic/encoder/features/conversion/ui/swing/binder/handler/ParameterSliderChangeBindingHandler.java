package com.acoustic.encoder.features.conversion.ui.swing.binder.handler;

import com.acoustic.encoder.features.conversion.ui.swing.binder.actions.ChangeParameterSliderAction;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.SliderChangeBindingHandler;

import java.util.List;

public class ParameterSliderChangeBindingHandler implements BindingHandler {

    private final ParameterSliderPanel panel;
    private final Runnable action;

    public ParameterSliderChangeBindingHandler(ParameterSliderPanel panel,  Runnable action) {
        if (panel == null) throw new IllegalArgumentException("Panel cannot be null");
        this.panel = panel;

        if (action == null) throw new IllegalArgumentException("Action cannot be null");
        this.action = action;
    }

    @Override
    public void bind(List<Runnable> removers) {
        ChangeParameterSliderAction action = new ChangeParameterSliderAction(panel, this.action);
        SliderChangeBindingHandler handler = new SliderChangeBindingHandler(panel.getSlider(), action);
        handler.bind(removers);
    }

}
