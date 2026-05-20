package com.acoustic.encoder.infrastructure.ui_shared.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.icons.ScaledFlatRadioButtonIcon;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SwingRadioButtonGroup extends ButtonGroup {

    private static final String ILLEGAL_STARTING_OPTION_MSG = "Starting option not among the options";

    private static final int ICON_TEXT_GAP = (int) (4 * SwingUtils.getScreenScaleRatio());

    private final List<JRadioButton> buttons = new ArrayList<>();

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

            Icon scaledIcon = new ScaledFlatRadioButtonIcon(SwingUtils.getScreenScaleRatio());

            for (String option : options) {
                JRadioButton button;

                if (option.equals(startingOption))
                    button = new JRadioButton(option, true);
                else
                    button = new JRadioButton(option);

                if (font != null) button.setFont(font);

                if (fontSize > 0)
                    button.setFont(new Font(button.getFont().getName(), button.getFont().getStyle(), fontSize));

                button.setIcon(scaledIcon);

                button.setSelectedIcon(Objects.requireNonNullElse(selectedIcon, scaledIcon));

                button.setVerticalTextPosition(SwingConstants.BOTTOM);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setIconTextGap(ICON_TEXT_GAP);

                this.add(button);
                buttons.add(button);
            }

        }
    }

    public int getSelectedIndex() {
        for (int i = 0; i < buttons.size(); i++)
            if (buttons.get(i).isSelected()) return i;
        
        return -1;
    }

    public List<JRadioButton> getButtons() {
        return buttons;
    }
}
