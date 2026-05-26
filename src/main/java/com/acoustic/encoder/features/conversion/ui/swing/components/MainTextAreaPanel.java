package com.acoustic.encoder.features.conversion.ui.swing.components;

import com.acoustic.encoder.features.conversion.ui.swing.binder.updater.TextAreaUpdater;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.*;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class MainTextAreaPanel extends SwingPanel {

    private static final String NULL_SCROLL_PANE_ERROR_MSG = "Scroll pane cannot be null";
    private static final String NULL_LABEL_ERROR_MSG = "Label cannot be null";

    private static final int VERTICAL_STRUT = 10;

    private final SwingTextArea textArea;

    public MainTextAreaPanel(SwingVerticalScrollPane scrollPane, SwingLabel label) {
        if (scrollPane == null) throw new IllegalArgumentException(NULL_SCROLL_PANE_ERROR_MSG);
        if (label == null) throw new IllegalArgumentException(NULL_LABEL_ERROR_MSG);

        this.textArea = (SwingTextArea) scrollPane.getComponent();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);
        add(Box.createVerticalStrut((int)(VERTICAL_STRUT * SwingUtils.getScreenScaleRatio())));
        add(scrollPane);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public TextAreaUpdater getTextAreaUpdater() {
        return this::setText;
    }

    public String getText() {
        return textArea.getText();
    }

    public boolean isTextEmpty() {
        return getText().trim().isEmpty();
    }

}
