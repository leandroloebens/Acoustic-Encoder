package com.acoustic.encoder.shared.view.swing.components;

import javax.swing.*;
import java.awt.*;

public class SwingPanel extends JPanel {

    public SwingPanel() {}

    public SwingPanel(LayoutManager layout) {
        if (layout != null) this.setLayout(layout);
    }

}
