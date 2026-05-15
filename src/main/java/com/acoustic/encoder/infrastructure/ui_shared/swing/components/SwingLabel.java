package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingLabel extends JLabel {

    public SwingLabel() {
        setText("");
    }

    public SwingLabel(int fontSize) {
        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));
    }

    public SwingLabel(String text, Font font, int fontSize, Border border) {
        if (text != null) this.setText(text);

        if (font != null) this.setFont(font);

        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));

        if (border != null) this.setBorder(border);
    }

}
