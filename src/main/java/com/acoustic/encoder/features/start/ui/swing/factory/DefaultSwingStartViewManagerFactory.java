package com.acoustic.encoder.features.start.ui.swing.factory;

import com.acoustic.encoder.features.start.ui.StartViewManager;
import com.acoustic.encoder.features.start.ui.factory.StartViewManagerFactory;
import com.acoustic.encoder.features.start.ui.swing.DefaultSwingStartViewManager;
import com.acoustic.encoder.features.start.ui.swing.components.factory.DefaultSwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.ui.swing.components.factory.SwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.ui.swing.frame.assembler.DefaultSwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.ui.swing.frame.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.ui.swing.frame.binder.DefaultSwingStartViewFrameBinder;
import com.acoustic.encoder.infrastructure.ui_shared.ViewConfigLoader;

public class DefaultSwingStartViewManagerFactory implements StartViewManagerFactory {

    private final static String START_VIEW_CONFIG_FILE = "startViewMapping.properties";

    public DefaultSwingStartViewManagerFactory() {
    }

    @Override
    public StartViewManager createViewManager() {
        return new DefaultSwingStartViewManager(
                getStartViewAssembler(),
                new DefaultSwingStartViewFrameBinder()
        );
    }

    private SwingStartViewFrameAssembler getStartViewAssembler() {
        ViewConfigLoader startViewConfigLoader = new ViewConfigLoader(START_VIEW_CONFIG_FILE);

        SwingStartViewComponentsFactory startViewComponentsFactory =
                new DefaultSwingStartViewComponentsFactory(startViewConfigLoader.loadConfigMap());

        return new DefaultSwingStartViewFrameAssembler(startViewComponentsFactory.createComponents());
    }
}
