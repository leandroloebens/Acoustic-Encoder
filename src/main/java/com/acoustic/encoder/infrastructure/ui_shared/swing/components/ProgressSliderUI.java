package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import com.formdev.flatlaf.ui.FlatSliderUI;

import java.awt.*;

public class ProgressSliderUI extends FlatSliderUI {
    private static final int TRACK_HEIGHT = 5;
    private static final int THUMB_SIZE = 10;

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = thumbRect.x + (thumbRect.width - THUMB_SIZE) / 2;
        int y = thumbRect.y + (thumbRect.height - THUMB_SIZE) / 2;

        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, THUMB_SIZE, THUMB_SIZE);

        g2.dispose();
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerY = trackRect.y + trackRect.height / 2;
        int startX = trackRect.x;
        int endX = trackRect.x + trackRect.width;

        g2.setColor(new Color(80, 80, 80));
        g2.fillRoundRect(startX, centerY - TRACK_HEIGHT / 2, endX - startX, TRACK_HEIGHT, TRACK_HEIGHT, TRACK_HEIGHT);

        int filledWidth = thumbRect.x + thumbRect.width / 2 - startX;

        g2.setColor(new Color(0, 209, 228));
        g2.fillRoundRect(startX, centerY - TRACK_HEIGHT / 2, filledWidth, TRACK_HEIGHT, TRACK_HEIGHT, TRACK_HEIGHT);

        g2.dispose();
    }
}
