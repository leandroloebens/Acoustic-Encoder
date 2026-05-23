package com.acoustic.encoder.features.conversion.ui.swing.binder.actions;

import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSlider;

public class ChangeParameterSliderAction implements Runnable {

    private final ParameterSliderPanel panel;
    private final Runnable parameterSyncAction;

    public ChangeParameterSliderAction(ParameterSliderPanel panel, Runnable action) {
        if (panel == null) throw new IllegalArgumentException("Panel cannot be null");
        this.panel = panel;

        if  (action == null) throw new IllegalArgumentException("Action cannot be null");
        this.parameterSyncAction = action;
    }

    @Override
    public void run() {
        SwingSlider slider = panel.getSlider();
        int value = slider.getValue();

        if (value < slider.getMinToShow()) slider.setValue(slider.getMinToShow());
        else if (value > slider.getMaxToShow()) slider.setValue(slider.getMaxToShow());

        panel.updateLabel();
        parameterSyncAction.run();
    }
}
