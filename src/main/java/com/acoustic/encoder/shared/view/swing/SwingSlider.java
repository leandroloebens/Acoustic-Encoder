package com.acoustic.encoder.shared.view.swing;

import javax.swing.*;

public class SwingSlider extends JSlider {

    public SwingSlider(int direction, int min, int max, int startValue, int tickSpacing, boolean showPaintLabels) {
        this.setOrientation(direction);
        this.setMinimum(min);
        this.setMaximum(max);
        this.setValue(startValue);
        this.setMajorTickSpacing(tickSpacing);
        this.setPaintTicks(true);
        this.setPaintLabels(showPaintLabels);
    }

}
