package com.acoustic.encoder.features.conversion.view.swing.components;

import com.acoustic.encoder.shared.view.swing.components.SwingComboBox;
import com.acoustic.encoder.shared.view.swing.components.SwingLabel;
import com.acoustic.encoder.shared.view.swing.components.SwingPanel;

import javax.swing.*;
import java.awt.*;

public class ParameterComboBoxPanel<T> extends SwingPanel {

    private SwingComboBox<T> comboBox;

    public ParameterComboBoxPanel(
            SwingComboBox<T> comboBox,
            SwingLabel label,
            Dimension preferredSize,
            Dimension maxSize
    ) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if (comboBox != null) this.comboBox = comboBox;
        
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(label);
        this.add(this.comboBox);

        if (preferredSize != null) this.setPreferredSize(preferredSize);
        if (maxSize != null) this.setMaximumSize(maxSize);

    }

    public SwingComboBox<T> getComboBox() {
        return this.comboBox;
    }

}
