package com.acoustic.encoder.infrastructure.ui_shared.swing.icons;

import javax.swing.*;

public class IconLoader {

    public static Icon load(String path) {
        return new ImageIcon(IconLoader.class.getResource(path));
    }
}
