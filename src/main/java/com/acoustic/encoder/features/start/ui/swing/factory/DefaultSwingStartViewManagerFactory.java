package com.acoustic.encoder.features.start.ui.swing.factory;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.ui.StartViewManager;
import com.acoustic.encoder.features.start.ui.factory.StartViewManagerFactory;
import com.acoustic.encoder.features.start.ui.swing.DefaultSwingStartViewManager;
import com.acoustic.encoder.features.start.ui.swing.components.factory.DefaultSwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.ui.swing.components.factory.SwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.ui.swing.assembler.DefaultSwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.ui.swing.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.ui.swing.binder.DefaultSwingStartViewEventBinder;
import com.acoustic.encoder.infrastructure.ui_shared.ViewConfigLoader;


public class DefaultSwingStartViewManagerFactory implements StartViewManagerFactory {

    private final static String START_VIEW_CONFIG_FILE = "startViewMapping.properties";

    private final EventBus eventBus;

    public DefaultSwingStartViewManagerFactory(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;
    }

    @Override
    public StartViewManager createViewManager() {
        return new DefaultSwingStartViewManager(
                getStartViewAssembler(),
                new DefaultSwingStartViewEventBinder(),
                eventBus
        );
    }

    private SwingStartViewFrameAssembler getStartViewAssembler() {
        ViewConfigLoader startViewConfigLoader = new ViewConfigLoader(START_VIEW_CONFIG_FILE);

        SwingStartViewComponentsFactory startViewComponentsFactory =
                new DefaultSwingStartViewComponentsFactory(startViewConfigLoader.loadConfigMap());

        return new DefaultSwingStartViewFrameAssembler(startViewComponentsFactory.createComponents());
    }
}
