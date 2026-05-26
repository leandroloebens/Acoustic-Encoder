package com.acoustic.encoder.features.conversion.ui.swing.binder.provider;

import com.acoustic.encoder.features.conversion.ui.swing.components.MainTextAreaPanel;

public class MainTextAreaInputProvider implements TextInputProvider {

    private final MainTextAreaPanel mainTextAreaPanel;

    public MainTextAreaInputProvider(MainTextAreaPanel mainTextAreaPanel) {
        if  (mainTextAreaPanel == null) throw new IllegalArgumentException("Text panel cannot be null");
        this.mainTextAreaPanel = mainTextAreaPanel;
    }

    @Override
    public String getTextInput() {
        return mainTextAreaPanel.getText();
    }
}
