package com.acoustic.encoder.features.start.view.swing.components.factory;

import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingButton;
import com.acoustic.encoder.shared.view.swing.components.SwingLabel;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import java.util.HashMap;

public class DefaultSwingStartViewComponentsFactory implements SwingStartViewComponentsFactory {

    private static final String ILLEGAL_CONFIG_ARGUMENT_MESSAGE = "Illegal config argument: ";

    private final SwingViewConfigWrapper config;

    public DefaultSwingStartViewComponentsFactory(HashMap<String,String> configMap) {
        if (configMap == null) throw new IllegalArgumentException(ILLEGAL_CONFIG_ARGUMENT_MESSAGE + null);
        this.config = new SwingViewConfigWrapper(configMap);
    }

    @Override
    public StartViewSwingComponentsWrapper createComponents() {
        SwingLabel titleLabel = new SwingLabel(
                config.getString("TITLE_LABEL_TEXT"),
                null,
                config.getScaledInt("TITLE_LABEL_FONT_SIZE"),
                null
        );

        SwingButton openProjectButton = new SwingButton(
                config.getString("OPEN_PROJECT_BUTTON_TEXT"),
                null,
                config.getScaledInt("OPEN_PROJECT_BUTTON_FONT_SIZE"),
                null,
                null
        );

        SwingButton newProjectButton = new SwingButton(
                config.getString("NEW_PROJECT_BUTTON_TEXT"),
                null,
                config.getScaledInt("NEW_PROJECT_BUTTON_FONT_SIZE"),
                null,
                null
        );

        SwingUtils.setHandCursor(openProjectButton, newProjectButton);

        return new StartViewSwingComponentsWrapper(
                titleLabel,
                openProjectButton,
                newProjectButton
        );
    }
}
