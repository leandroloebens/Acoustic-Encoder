package com.acoustic.encoder.shared.view.swing;

import javax.swing.*;

public class SwingFrame extends JFrame {

    public SwingFrame(String title, int windowWidth, int windowHeight, int exitOperation) {
        // Set checks for each variable
        super(title);
        this.setSize(windowWidth, windowHeight);
        this.setDefaultCloseOperation(exitOperation);
    }

}
