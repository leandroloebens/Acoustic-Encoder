package com.acoustic.encoder.features.conversion.ui.swing.manager;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.service.mapper.DefaultConversionParametersService;
import com.acoustic.encoder.features.conversion.ui.ConversionViewManager;
import com.acoustic.encoder.features.conversion.ui.ConversionViewManagerFactory;
import com.acoustic.encoder.features.conversion.ui.swing.assembler.DefaultSwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.ui.swing.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.ui.swing.binder.DefaultSwingConversionViewEventBinder;
import com.acoustic.encoder.features.conversion.ui.swing.binder.SwingConversionViewEventBinder;
import com.acoustic.encoder.features.conversion.ui.swing.components.factory.DefaultSwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.conversion.ui.swing.components.factory.SwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.DefaultSwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.audio.export.MidiInstrumentListProvider;
import com.acoustic.encoder.infrastructure.ui_shared.config.ViewConfigLoader;


public class DefaultSwingConversionViewManagerFactory implements ConversionViewManagerFactory {

    private static final String ILLEGAL_EVENT_BUS_ARGUMENT = "Event bus cannot be null";

    private static final String CONVERSION_VIEW_CONFIG_FILE = "conversionViewMapping.properties";
    private static final int MIDI_INSTRUMENT_BANK = 0;

    private final EventBus eventBus;

    public DefaultSwingConversionViewManagerFactory(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException(ILLEGAL_EVENT_BUS_ARGUMENT);
        this.eventBus = eventBus;
    }

    @Override
    public ConversionViewManager createViewManager(ConversionController controller) {
        SwingConversionViewFrameAssembler conversionViewAssembler = getConversionViewAssembler();

        ConversionParametersService parametersService = new DefaultConversionParametersService();

        SwingConversionViewEventBinder conversionViewBinder =
                new DefaultSwingConversionViewEventBinder(
                        eventBus, parametersService, DefaultSwingConversionViewSynchronizer::new
                );

        return new DefaultSwingConversionViewManager(
                controller, conversionViewAssembler, conversionViewBinder);
    }

    private SwingConversionViewFrameAssembler getConversionViewAssembler() {
        ViewConfigLoader conversionViewConfigLoader =
                new ViewConfigLoader(CONVERSION_VIEW_CONFIG_FILE);

        SwingConversionViewComponentsFactory conversionViewComponentsFactory =
                new DefaultSwingConversionViewComponentsFactory(
                        conversionViewConfigLoader.loadConfigMap(),
                        new MidiInstrumentListProvider(MIDI_INSTRUMENT_BANK)
                );

        return new DefaultSwingConversionViewFrameAssembler(conversionViewComponentsFactory.createComponents());
    }

}
