package com.acoustic.encoder.features.player.view.swing.factory;

import com.acoustic.encoder.features.player.view.PlayerViewManager;
import com.acoustic.encoder.features.player.view.PlayerViewManagerFactory;
import com.acoustic.encoder.features.player.view.swing.DefaultSwingPlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.assembler.DefaultSwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.assembler.SwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.binder.DefaultSwingPlayerViewEventBinder;
import com.acoustic.encoder.features.player.view.swing.binder.SwingPlayerViewEventBinder;
import com.acoustic.encoder.features.player.view.swing.components.factory.DefaultSwingPlayerViewComponentsFactory;
import com.acoustic.encoder.features.player.view.swing.components.factory.SwingPlayerViewComponentsFactory;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

public class DefaultSwingPlayerViewManagerFactory implements PlayerViewManagerFactory {

    private static final String ILLEGAL_EVENT_BUS_ARGUMENT = "Event bus cannot be null";
    private static final String PLAYER_VIEW_CONFIG_FILE = "playerViewMapping.properties";

    private final EventBus eventBus;

    public DefaultSwingPlayerViewManagerFactory(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException(ILLEGAL_EVENT_BUS_ARGUMENT);
        this.eventBus = eventBus;
    }

    @Override
    public PlayerViewManager createViewManager() {
        SwingPlayerViewAssembler playerViewAssembler = getPlayerViewAssembler();

        SwingPlayerViewEventBinder playerViewBinder = new DefaultSwingPlayerViewEventBinder(eventBus);

        return new DefaultSwingPlayerViewManager(playerViewAssembler, playerViewBinder, eventBus);
    }

    private SwingPlayerViewAssembler getPlayerViewAssembler() {
        ViewConfigLoader configLoader = new ViewConfigLoader(PLAYER_VIEW_CONFIG_FILE);

        SwingPlayerViewComponentsFactory playerViewComponentsFactory =
                new DefaultSwingPlayerViewComponentsFactory(configLoader.loadConfigMap());

        return new DefaultSwingPlayerViewAssembler(playerViewComponentsFactory.createComponents());
    }
}
