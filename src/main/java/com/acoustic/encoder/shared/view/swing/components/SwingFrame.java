package com.acoustic.encoder.shared.view.swing.components;

import javax.swing.*;
import java.awt.*;

public class SwingFrame extends JFrame {

    public SwingFrame() {}

    public SwingFrame(String title, Dimension windowSize, int exitOperation) {
        // Set checks for each variable
        super(title);
        this.setSize(windowSize);
        this.setDefaultCloseOperation(exitOperation);
    }

}
