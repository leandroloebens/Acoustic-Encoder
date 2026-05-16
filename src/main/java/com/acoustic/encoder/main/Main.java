package com.acoustic.encoder.main;

import com.acoustic.encoder.features.conversion.config.DefaultParserConfigFactory;
import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.conversion.parser.DefaultInstructionParser;
import com.acoustic.encoder.features.conversion.parser.DefaultVoiceParser;
import com.acoustic.encoder.features.conversion.parser.RoundRobinVoiceConfigSelector;
import com.acoustic.encoder.infrastructure.audio.midi.DefaultSequenceBuilder;
import com.acoustic.encoder.infrastructure.audio.midi.DefaultSequencePlayer;
import com.acoustic.encoder.infrastructure.audio.midi.JSoundAudioAdapter;
import com.acoustic.encoder.infrastructure.file.DefaultFIleService;
import com.acoustic.encoder.infrastructure.audio.midi.command.DefaultMidiCommandRegistryFactory;
import com.acoustic.encoder.infrastructure.audio.midi.track.DefaultTrackWriter;
import com.acoustic.encoder.infrastructure.audio.export.midi.MidiFileExporter;
import com.acoustic.encoder.features.player.listener.PlayerAppShutdownListener;
import com.acoustic.encoder.features.player.listener.PlayerConversionCompletedListener;
import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.infrastructure.event.DefaultEventBus;
import com.acoustic.encoder.main.navigation.DefaultAppNavigator;
import com.acoustic.encoder.features.conversion.config.DefaultParsingConfigLoader;
import com.acoustic.encoder.main.factory.DefaultScreenFactory;
import com.acoustic.encoder.features.player.service.DefaultAudioPlayerService;
import com.acoustic.encoder.features.conversion.service.DefaultConversionService;
import com.acoustic.encoder.main.navigation.listener.NavigationConversionCompletedListener;
import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatDarculaLaf;
//import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatIntelliJLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.sound.midi.MidiSystem;

public class Main {

    void main() throws Exception {
        // Set Look and Feel
        FlatLaf.registerCustomDefaultsSource("themes");
        FlatDarkLaf.setup();
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        FlatLightLaf.setup();
//        FlatDarculaLaf.setup();
//        FlatIntelliJLaf.setup();

        // Event Bus
        var eventBus = new DefaultEventBus();


        // Conversion Service
        var parserConfigLoader = new DefaultParsingConfigLoader(DefaultParsingConfigLoader.CONFIG_FILE_NAME);
        var parserConfigFactory = new DefaultParserConfigFactory();

        var instructionParser = new DefaultInstructionParser(
                parserConfigFactory.create(parserConfigLoader.loadConfigMap())
        );
        var voiceConfigSelector = new RoundRobinVoiceConfigSelector();

        var voiceParser = new DefaultVoiceParser(instructionParser, voiceConfigSelector);

        var conversionService = new DefaultConversionService(voiceParser, eventBus);


        // File Service
        var fileService = new DefaultFIleService();


        // Audio Player Service
        var commandRegistryFactory = new DefaultMidiCommandRegistryFactory();
        var commandRegistry = commandRegistryFactory.create();
        var trackWriter = new DefaultTrackWriter(commandRegistry);
        var sequenceBuilder = new DefaultSequenceBuilder(trackWriter);
        var sequencePlayer = new DefaultSequencePlayer(MidiSystem.getSequencer(false));

        var audioPlayer = new JSoundAudioAdapter(sequenceBuilder, sequencePlayer);
        var musicExporter = new MidiFileExporter(sequencePlayer);

        var audioPlayerService = new DefaultAudioPlayerService(audioPlayer, musicExporter);


        // Navigation
        var screenFactory = new DefaultScreenFactory(
                eventBus,
                conversionService,
                fileService,
                audioPlayerService
        );
        var appNavigator = new DefaultAppNavigator(screenFactory, eventBus);


        // subscribes listeners to their events
        eventBus.subscribe(
                ConversionCompletedEvent.class,
                new PlayerConversionCompletedListener(audioPlayerService)
        );
        eventBus.subscribe(
                ConversionCompletedEvent.class,
                new NavigationConversionCompletedListener(appNavigator)
        );
        eventBus.subscribe(
                AppShutdownEvent.class,
                new PlayerAppShutdownListener(audioPlayerService)
        );

        appNavigator.startApp();

    }
}
