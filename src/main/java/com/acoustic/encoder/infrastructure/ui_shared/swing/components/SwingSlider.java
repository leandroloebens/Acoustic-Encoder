package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.awt.*;

import javax.swing.*;

public class SwingSlider extends JSlider {

    private static final int MAX_SLIDER_FONT_SIZE = (int) (10 * SwingUtils.getScreenScaleRatio());
    private static final int MIN_SCREEN_HEIGHT = 1080;

    private int minToShow;
    private int maxToShow;

    public SwingSlider() {
    }

    public SwingSlider(
            int direction,
            int min,
            int minToShow,
            int max,
            int maxToShow,
            int startValue,
            int tickSpacing,
            Dimension preferredSize,
            Dimension maxSize
    ) {

        this.setOrientation(direction);
        this.setMinimum(min);
        this.setMaximum(max);
        this.setValue(startValue);
        this.setMajorTickSpacing(tickSpacing);
        this.setPaintTicks(true);
        this.setPaintLabels(false);

        if (minToShow < min) {
            throw new IllegalArgumentException("minToShow must be greater than or equal to min");
        }
        if (maxToShow > max) {
            throw new IllegalArgumentException("maxToShow must be less than or equal to max");
        }

        this.minToShow = minToShow;
        this.maxToShow = maxToShow;

        if (preferredSize != null) this.setPreferredSize(preferredSize);
        if (maxSize != null) this.setMaximumSize(maxSize);
    }

    public int getMinToShow() { return minToShow; }

    public void setMinToShow(int minToShow) {
        if (minToShow > this.getMinimum())
            this.minToShow = minToShow;
        else
            throw new IllegalArgumentException("minToShow cannot be less than minimum value");
    }

    public int getMaxToShow() { return maxToShow; }

    public void setMaxToShow(int maxToShow) {
        if (maxToShow < this.getMaximum())
            this.maxToShow = maxToShow;
        else
            throw new IllegalArgumentException("maxToShow cannot be greater than maximum value");
    }

}
