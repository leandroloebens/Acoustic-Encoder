package com.acoustic.encoder.shared.view.swing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingTextArea extends JTextArea {

    public SwingTextArea(Font font, Border border) {
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        if (border != null) this.setBorder(border);
        if (font != null) this.setFont(font);
    }

    public String getText() { return super.getText(); }
}
