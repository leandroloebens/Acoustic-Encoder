package com.acoustic.encoder.features.conversion.view.swing.components;

import com.acoustic.encoder.shared.view.swing.SwingLabel;
import com.acoustic.encoder.shared.view.swing.SwingPanel;
import com.acoustic.encoder.shared.view.swing.SwingSlider;

import java.awt.*;

public class ParameterPanel extends SwingPanel {
    private final SwingSlider slider;
    private final SwingLabel label;

    public ParameterPanel(LayoutManager layout, SwingSlider slider, SwingLabel label) {
        super(layout);

        this.slider = slider;
        this.label = label;

        this.add(this.slider);
        this.add(this.label);
    }

    public SwingSlider getSlider() { return this.slider; }

    public SwingLabel getLabel() { return this.label; }

}
