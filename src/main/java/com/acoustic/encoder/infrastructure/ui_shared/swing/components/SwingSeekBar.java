package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import java.awt.*;

public class SwingSeekBar extends SwingSlider {

    public SwingSeekBar(
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
        super(direction, min, minToShow, max, maxToShow, startValue, tickSpacing, preferredSize, maxSize);
    }
}
