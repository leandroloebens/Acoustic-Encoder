package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingButton extends JButton {

    public SwingButton() {}

    public SwingButton(String text, Font font, int fontSize, Border border, Dimension size) {
        if (text != null) this.setText(text);

        if (border != null) this.setBorder(border);

        if (font != null) this.setFont(font);

        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));

        if (size != null) this.setPreferredSize(size);

        this.setFocusPainted(false);
    }

    public SwingButton(String text, Icon icon, Font font, int fontSize, Border border, Dimension size) {
        if (text != null) this.setText(text);

        if (icon != null) this.setIcon(icon);

        if (border != null) this.setBorder(border);

        if (font != null) this.setFont(font);

        if (fontSize > 0) this.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), fontSize));

        if (size != null) this.setPreferredSize(size);

        this.setFocusPainted(false);
    }

}
