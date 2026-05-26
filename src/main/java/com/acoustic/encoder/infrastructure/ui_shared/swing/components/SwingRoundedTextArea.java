package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingRoundedTextArea extends SwingTextArea {

    private static final int MIN_BORDER_GAP = (int) (7 * SwingUtils.getScreenScaleRatio());

    private final int arc;

    public SwingRoundedTextArea(Font font, int fontSize, Border border, int arc) {
        super(font, fontSize, border);

        this.arc = arc;

        setOpaque(false);

        if (border == null) setBorder(
                BorderFactory.createEmptyBorder(MIN_BORDER_GAP, MIN_BORDER_GAP, MIN_BORDER_GAP, MIN_BORDER_GAP));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        g2.dispose();

        super.paintComponent(g);
    }
}
