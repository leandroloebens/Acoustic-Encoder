package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingTextArea extends JTextArea {

    public SwingTextArea() {}

    public SwingTextArea(Font font, int fontSize, Border border) {
        this.setLineWrap(true);

        this.setWrapStyleWord(true);

        if (font != null) this.setFont(font);

        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));

        if (border != null) this.setBorder(border);
    }

    public String getText() { return super.getText(); }
}
