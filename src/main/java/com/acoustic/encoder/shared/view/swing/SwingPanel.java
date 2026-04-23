package com.acoustic.encoder.shared.view.swing;

import javax.swing.*;
import java.awt.*;

public class SwingPanel extends JPanel {

    public SwingPanel(LayoutManager layout) {
        if (layout != null) this.setLayout(layout);
    }

}
