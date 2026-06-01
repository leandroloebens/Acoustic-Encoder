package com.acoustic.encoder.main;

import com.acoustic.encoder.domain.event.AppShutdownEvent;
import com.acoustic.encoder.domain.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.conversion.parser.DefaultInstructionParser;
import com.acoustic.encoder.features.conversion.parser.DefaultVoiceParser;
import com.acoustic.encoder.features.conversion.parser.RoundRobinVoiceConfigSelector;
import com.acoustic.encoder.features.conversion.parser.config.DefaultParserConfigFactory;
import com.acoustic.encoder.features.conversion.parser.config.DefaultParsingConfigLoader;
import com.acoustic.encoder.features.conversion.service.DefaultConversionService;
import com.acoustic.encoder.features.player.listener.PlayerAppShutdownListener;
import com.acoustic.encoder.features.player.listener.PlayerConversionCompletedListener;
import com.acoustic.encoder.features.player.service.DefaultAudioPlayerService;
import com.acoustic.encoder.features.start.service.DefaultStartService;
import com.acoustic.encoder.infrastructure.audio.export.MidiFileExporter;
import com.acoustic.encoder.infrastructure.audio.player.DefaultSequenceBuilder;
import com.acoustic.encoder.infrastructure.audio.player.DefaultSequencePlayer;
import com.acoustic.encoder.infrastructure.audio.player.JSoundAudioAdapter;
import com.acoustic.encoder.infrastructure.audio.player.command.DefaultMidiCommandRegistryFactory;
import com.acoustic.encoder.infrastructure.audio.player.track.DefaultTrackWriter;
import com.acoustic.encoder.infrastructure.event.DefaultEventBus;
import com.acoustic.encoder.infrastructure.file.FileTextRepository;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingFontUtils;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;
import com.acoustic.encoder.main.factory.DefaultScreenFactory;
import com.acoustic.encoder.main.navigation.DefaultAppNavigator;
import com.acoustic.encoder.main.navigation.listener.NavigationConversionCompletedListener;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.sound.midi.MidiSystem;
import javax.swing.*;

public class Main {

    void main() throws Exception {
        // Register custom font
        SwingFontUtils.loadFontsToSystem("ui/fonts");

        // Set Look and Feel
        FlatLaf.registerCustomDefaultsSource("themes");
        System.setProperty(
                "flatlaf.uiScale",
                String.format(java.util.Locale.US, "%.2fx", SwingUtils.getScreenScaleRatio())
        );
        FlatDarkLaf.setup();


        // Event Bus
        var eventBus = new DefaultEventBus();


        // Start Service
        var startService = new DefaultStartService("defaultMusicProject.properties");


        // Conversion Service
        var parserConfigLoader = new DefaultParsingConfigLoader(DefaultParsingConfigLoader.DEFAULT_ENCODER_MAPPING_PATH);
        var parserConfigFactory = new DefaultParserConfigFactory();

        var instructionParser = new DefaultInstructionParser(
                parserConfigFactory.create(parserConfigLoader.loadConfigMap())
        );
        var voiceConfigSelector = new RoundRobinVoiceConfigSelector();

        var voiceParser = new DefaultVoiceParser(instructionParser, voiceConfigSelector);

        var conversionService = new DefaultConversionService(voiceParser);


        // File Service
        var fileTextRepository = new FileTextRepository();


        // Audio Player Service
        var commandRegistryFactory = new DefaultMidiCommandRegistryFactory();
        var commandRegistry = commandRegistryFactory.create();
        var trackWriter = new DefaultTrackWriter(commandRegistry);
        var sequenceBuilder = new DefaultSequenceBuilder(trackWriter);
        var sequencePlayer = new DefaultSequencePlayer(MidiSystem.getSequencer());

        var audioPlayer = new JSoundAudioAdapter(sequenceBuilder, sequencePlayer);
        var musicExporter = new MidiFileExporter(sequencePlayer);

        var audioPlayerService = new DefaultAudioPlayerService(audioPlayer, musicExporter);


        // Navigation
        var screenFactory = new DefaultScreenFactory(
                eventBus,
                startService,
                conversionService,
                fileTextRepository,
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
