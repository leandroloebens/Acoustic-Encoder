package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerFooterComponent extends JPanel {

    private final static String SAVE_BUTTON_TEXT = "Save";

    private final JProgressBar progressBar;
//    private final JButton saveButton;

    private final SwingButton saveButton;

    public PlayerFooterComponent(SwingButton saveButton) {
//        this.saveButton = new JButton(SAVE_BUTTON_TEXT);
        this.saveButton = saveButton;
        this.progressBar = new JProgressBar();

        initializeComponent();
    }

    private void initializeComponent() {
        //setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        SwingUtils.setHandCursor(saveButton);

        //setBackground(Color.BLUE);
        setBackground(Color.darkGray);

        add(progressBar, BorderLayout.CENTER);
        add(saveButton, BorderLayout.EAST);

        updateProgress(10);
    }

    public void updateProgress(int value) {
        progressBar.setValue(value);
    }

    public SwingButton getSaveButton() {
        return this.saveButton;
    }
}
