package com.acoustic.encoder.features.conversion.view.swing.components;

import com.acoustic.encoder.shared.view.swing.components.SwingLabel;
import com.acoustic.encoder.shared.view.swing.components.SwingPanel;
import com.acoustic.encoder.shared.view.swing.components.SwingSlider;

import javax.swing.*;
import java.awt.*;

public class ParameterSliderPanel extends SwingPanel {
    private final SwingSlider slider;
    private final SwingLabel label;
    private final String labelText;

    public ParameterSliderPanel(SwingSlider slider, SwingLabel label, String labelText) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.slider = slider;
        this.label = label;
        this.labelText = labelText;

        this.add(this.label);
        this.add(this.slider);

        this.label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.slider.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.updateLabel();
    }

    public SwingSlider getSlider() { return this.slider; }

    public void updateLabel() {
        this.label.setText(this.labelText + this.slider.getValue());
    }

}
