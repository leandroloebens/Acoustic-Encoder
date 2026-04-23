package com.acoustic.encoder.shared.view.swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingLabel extends JLabel {

    public SwingLabel(String text, Font font, Border border) {
        if (text != null) this.setText(text);
        if (border != null) this.setBorder(border);
        if (font != null) this.setFont(font);
    }

}
