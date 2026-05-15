package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SwingVerticalScrollPane extends JScrollPane {

    public SwingVerticalScrollPane(JComponent component, Border border, Dimension maxSize) {
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }

        super(component);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        if (border != null) this.setBorder(border);

        if (maxSize != null) this.setMaximumSize(maxSize);
    }

    public JComponent getComponent() { return (JComponent) getViewport().getView(); }

}
