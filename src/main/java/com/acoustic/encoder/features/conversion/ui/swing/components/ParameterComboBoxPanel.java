package com.acoustic.encoder.features.conversion.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingComboBox;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;

import javax.swing.*;
import java.awt.*;

public class ParameterComboBoxPanel<T> extends SwingPanel {

    private final SwingComboBox<T> comboBox;

    public ParameterComboBoxPanel(
            SwingComboBox<T> comboBox,
            SwingLabel label,
            Dimension preferredSize,
            Dimension maxSize
    ) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if (comboBox == null) throw new IllegalArgumentException("ComboBox cannot be null");
        this.comboBox = comboBox;

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

    public T getSelectedItem() {
        int index = this.comboBox.getSelectedIndex();
        return (index >= 0) ? this.comboBox.getItemAt(index) : null;
    }

    public void setSelectedItemByIndex(int index) {
        this.comboBox.setSelectedByOriginalIndex(index);
    }

    public void setSelectedItem(T item) {
        this.comboBox.setSelectedItem(item);
    }

    public void setInitialItem(T initialItem) {
        this.comboBox.setInitialItem(initialItem);
    }

    public void resetItems() {
        this.comboBox.resetItems();
    }

    public JTextField getTextEditor() {
        return (JTextField) this.comboBox.getEditor().getEditorComponent();
    }

    public boolean isTextEditorInputValid() {
        return comboBox.finishEditing();
    }

    public void fireTextEditorActions() {
        getTextEditor().postActionEvent();
    }

}
