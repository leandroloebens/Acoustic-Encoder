package com.acoustic.encoder.shared.view.swing.components;

import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import java.awt.*;
import java.util.Hashtable;

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
            int sliderFontSize
    ) {

        this.setOrientation(direction);
        this.setMinimum(min);
        this.setMaximum(max);
        this.setValue(startValue);
        this.setMajorTickSpacing(tickSpacing);
        this.setPaintTicks(true);
        if (Toolkit.getDefaultToolkit().getScreenSize().height >= MIN_SCREEN_HEIGHT) this.setPaintLabels(true);

        if (sliderFontSize > MAX_SLIDER_FONT_SIZE) {
            sliderFontSize = MAX_SLIDER_FONT_SIZE;
        }

        if (minToShow < min) {
            throw new IllegalArgumentException("minToShow must be greater than or equal to min");
        }
        if (maxToShow > max) {
            throw new IllegalArgumentException("maxToShow must be less than or equal to max");
        }

        this.minToShow = minToShow;
        this.maxToShow = maxToShow;

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        labelTable.put(min, new JLabel(String.valueOf(minToShow)));
        labelTable.put(max, new JLabel(String.valueOf(maxToShow)));

        this.setLabelTable(labelTable);
        // Set font for all tick labels
        Font labelFont = new Font(this.getFont().getName(), this.getFont().getStyle(), (sliderFontSize > 0) ? sliderFontSize : this.getFont().getSize());
        for (JLabel label : labelTable.values()) {
            label.setFont(labelFont);
            label.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        }

        Dimension preferredSize = new Dimension(
                (int)(200 * SwingUtils.getScreenScaleRatio()),
                (int)(50 * SwingUtils.getScreenScaleRatio())
        );
        this.setPreferredSize(preferredSize);
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
