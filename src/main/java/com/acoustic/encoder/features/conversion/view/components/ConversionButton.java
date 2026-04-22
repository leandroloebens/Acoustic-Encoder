package com.acoustic.encoder.features.conversion.view.components;

import com.acoustic.encoder.shared.view.Button;

import javax.swing.*;
import java.awt.*;

public class ConversionButton implements Button<JButton> {

    private final JButton button = new JButton();

    @Override
    public JButton getNativeComponent() { return this.button; }

    @Override
    public void onClick(Runnable clickHandler) {
        this.button.addActionListener(event -> clickHandler.run());
    };
}
