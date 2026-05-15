package com.acoustic.encoder.infrastructure.ui_shared.swing.icons;

import com.formdev.flatlaf.icons.FlatRadioButtonIcon;
import javax.swing.*;
import java.awt.*;

public class ScaledFlatRadioButtonIcon implements Icon {
    private final FlatRadioButtonIcon delegate;
    private final float scale;

    public ScaledFlatRadioButtonIcon(float scale) {
        this.delegate = new FlatRadioButtonIcon();
        this.scale = scale;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            int scaledWidth = (int) (getIconWidth() * scale);
            int scaledHeight = (int) (getIconHeight() * scale);

            // Scale and translate to paint at the larger size
            g2.translate(x, y);
            g2.scale(scale, scale);
            delegate.paintIcon(c, g2, 0, 0);
        } finally {
            g2.dispose();
        }
    }

    @Override
    public int getIconWidth() {
        return (int) (delegate.getIconWidth() * scale);
    }

    @Override
    public int getIconHeight() {
        return (int) (delegate.getIconHeight() * scale);
    }
}
