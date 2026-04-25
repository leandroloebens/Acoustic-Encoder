package com.acoustic.encoder.shared.view.swing.components;

import javax.swing.*;
import javax.swing.border.Border;

public class SwingVerticalScrollPane extends JScrollPane {

    public SwingVerticalScrollPane(JComponent component, Border border) {
        if (component == null) {
            throw new IllegalArgumentException("Component cannot be null");
        }

        super(component);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        if (border != null) this.setBorder(border);
    }

    public JComponent getComponent() { return (JComponent) getViewport().getView(); }

}
