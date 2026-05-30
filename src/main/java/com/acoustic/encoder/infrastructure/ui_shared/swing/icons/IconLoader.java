package com.acoustic.encoder.infrastructure.ui_shared.swing.icons;

import javax.swing.*;
import java.awt.*;

public class IconLoader {

    public static Icon load(String path, Dimension size) {
        ImageIcon icon = new ImageIcon(IconLoader.class.getResource(path));

        Image scaledImage = icon.getImage().getScaledInstance(
                size.width,
                size.height,
                Image.SCALE_SMOOTH
        );

        return new ImageIcon(scaledImage);
    }
}
