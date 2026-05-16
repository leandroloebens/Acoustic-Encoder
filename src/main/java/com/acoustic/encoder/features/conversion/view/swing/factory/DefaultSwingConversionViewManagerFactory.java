package com.acoustic.encoder.features.conversion.view.swing.factory;

import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.service.mapper.DefaultConversionParametersService;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.features.conversion.view.ConversionViewManagerFactory;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionViewManager;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.DefaultSwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.SwingConversionViewComponentsFactory;
import com.acoustic.encoder.features.conversion.view.swing.assembler.DefaultSwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.view.swing.assembler.SwingConversionViewFrameAssembler;
import com.acoustic.encoder.features.conversion.view.swing.binder.DefaultSwingConversionViewEventBinder;
import com.acoustic.encoder.features.conversion.view.swing.binder.SwingConversionViewEventBinder;
import com.acoustic.encoder.features.player.export.midi.MidiInstrumentListProvider;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

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
    public ConversionViewManager createViewManager() {
        SwingConversionViewFrameAssembler conversionViewAssembler = getConversionViewAssembler();

        ConversionParametersService parametersService = new DefaultConversionParametersService();

        SwingConversionViewEventBinder conversionViewBinder =
                new DefaultSwingConversionViewEventBinder(eventBus, parametersService);

        return new DefaultSwingConversionViewManager(conversionViewAssembler, conversionViewBinder, eventBus);
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
