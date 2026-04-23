package com.acoustic.encoder.shared.view.swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingButton extends JButton {

    public SwingButton(String text, Font font, Border border) {
        if (text != null) this.setText(text);
        if (border != null) this.setBorder(border);
        if (font != null) this.setFont(font);
    }

}
