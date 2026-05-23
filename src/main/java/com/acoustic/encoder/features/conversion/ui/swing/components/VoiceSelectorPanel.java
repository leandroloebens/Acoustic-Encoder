package com.acoustic.encoder.features.conversion.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingRadioButtonGroup;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VoiceSelectorPanel extends SwingPanel {

    private final SwingRadioButtonGroup buttonGroup;
    private JRadioButton previousButton;

    public VoiceSelectorPanel(
            SwingLabel label,
            List<String> options,
            String startingOption,
            Font font,
            int fontSize,
            Icon selectedIcon
    ) {
        this.buttonGroup = new SwingRadioButtonGroup(options, startingOption, font, fontSize, selectedIcon);

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 10, 0);

        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        this.add(label, gbc);

        SwingPanel buttonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        for (JRadioButton button : buttonGroup.getButtons()) {
            buttonsPanel.add(button);
        }

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(buttonsPanel, gbc);

        previousButton = getSelectedButton();
    }

    public int getSelectedIndex() {
        return this.buttonGroup.getSelectedIndex();
    }

    public List<JRadioButton> getButtons() {
        return this.buttonGroup.getButtons();
    }

    public JRadioButton getSelectedButton() {
        return this.buttonGroup.getButtons().get(this.buttonGroup.getSelectedIndex());
    }

    public JRadioButton getPreviousButton() {
        return this.previousButton;
    }

    public void setPreviousButton(JRadioButton button) {
        if (button == null) throw new IllegalArgumentException("previousButton cannot be null");
        if (!getButtons().contains(button))
            throw new IllegalArgumentException("previousButton must be one of the buttons in the group");

        this.previousButton = button;
    }
}
