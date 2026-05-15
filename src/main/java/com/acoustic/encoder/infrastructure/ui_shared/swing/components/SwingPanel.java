package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import javax.swing.*;
import java.awt.*;

public class SwingPanel extends JPanel {

    public SwingPanel() {}

    public SwingPanel(LayoutManager layout) {
        if (layout != null) this.setLayout(layout);
    }

}
