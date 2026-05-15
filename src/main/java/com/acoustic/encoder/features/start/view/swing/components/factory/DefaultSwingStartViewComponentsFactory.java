package com.acoustic.encoder.features.start.view.swing.components.factory;

import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;

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
        SwingLabel titleLabel = new SwingLabel();
        SwingButton openProjectButton = new SwingButton();
        SwingButton newProjectButton = new SwingButton();

        return new StartViewSwingComponentsWrapper(
                titleLabel,
                openProjectButton,
                newProjectButton
        );
    }
}
