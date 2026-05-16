package com.acoustic.encoder.features.start.view.swing.factory;

import com.acoustic.encoder.features.start.view.StartViewManager;
import com.acoustic.encoder.features.start.view.StartViewManagerFactory;
import com.acoustic.encoder.features.start.view.swing.DefaultSwingStartViewManager;
import com.acoustic.encoder.features.start.view.swing.components.factory.DefaultSwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.view.swing.components.factory.SwingStartViewComponentsFactory;
import com.acoustic.encoder.features.start.view.swing.assembler.DefaultSwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.view.swing.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.view.swing.binder.DefaultSwingStartViewEventBinder;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

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
