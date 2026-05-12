package com.acoustic.encoder.shared.view.swing.components;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class SwingRadioButtonGroup extends ButtonGroup {

    private static final String ILLEGAL_STARTING_OPTION_MSG = "Starting option not among the options";

    public SwingRadioButtonGroup(
            List<String> options,
            String startingOption,
            Font font,
            int fontSize,
            Icon selectedIcon
    ) {
        if (options != null) {

            if (startingOption != null && !startingOption.isEmpty() && !options.contains(startingOption))
                throw new IllegalArgumentException(ILLEGAL_STARTING_OPTION_MSG);

            for (String option : options) {
                JRadioButton button;

                if (option.equals(startingOption))
                    button = new JRadioButton(option, true);
                else
                    button = new JRadioButton(option);

                if (font != null) button.setFont(font);

                if (fontSize > 0)
                    button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), fontSize));

                if (selectedIcon != null) button.setSelectedIcon(selectedIcon);

                this.add(button);
            }

        }
    }

    public int getSelectedIndex() {
        JRadioButton selected = (JRadioButton) getSelection();

        List<AbstractButton> options = Collections.list(getElements());

        return options.indexOf(selected);
    }
}
