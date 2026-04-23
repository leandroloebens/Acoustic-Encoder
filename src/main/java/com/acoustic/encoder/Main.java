package com.acoustic.encoder;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.features.conversion.view.swing.SwingConversionScreenAssembler;
import com.acoustic.encoder.features.conversion.view.ConversionScreenManager;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionScreenAssembler;
import com.acoustic.encoder.features.conversion.view.swing.DefaultSwingConversionScreenManager;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.ConversionScreenComponentsFactory;
import com.acoustic.encoder.features.conversion.view.swing.components.factory.SwingConversionScreenComponentsFactory;
import com.acoustic.encoder.features.player.listener.PlayerConversionCompletedListener;
import com.acoustic.encoder.shared.event.DefaultEventBus;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.navigation.AppNavigator;
import com.acoustic.encoder.shared.navigation.DefaultAppNavigator;
import com.acoustic.encoder.features.player.audio.AudioPlayer;
import com.acoustic.encoder.features.player.audio.midi.DefaultSequenceBuilder;
import com.acoustic.encoder.features.player.audio.midi.DefaultSequencePlayer;
import com.acoustic.encoder.features.player.audio.midi.JSoundAudioAdapter;
import com.acoustic.encoder.features.conversion.config.ConfigLoader;
import com.acoustic.encoder.shared.factory.DefaultScreenFactory;
import com.acoustic.encoder.shared.factory.ScreenFactory;
import com.acoustic.encoder.features.conversion.parser.InstructionParser;
import com.acoustic.encoder.features.conversion.parser.TextToInstructionParser;
import com.acoustic.encoder.features.player.service.AudioPlayerService;
import com.acoustic.encoder.features.player.service.DefaultAudioPlayerService;
import com.acoustic.encoder.features.conversion.service.DefaultConversionService;
import com.acoustic.encoder.features.conversion.service.ConversionService;
import com.acoustic.encoder.shared.navigation.listener.NavigationConversionCompletedListener;

import javax.sound.midi.MidiSystem;

public class Main {

    void main() throws Exception {

        ConfigLoader configLoader = new ConfigLoader(ConfigLoader.CONFIG_FILE_NAME);
        InstructionParser parser = new TextToInstructionParser(configLoader.loadConfigMap());

        EventBus eventBus = new DefaultEventBus();
        ConversionService conversionService = new DefaultConversionService(parser, eventBus);

        ConversionScreenComponentsFactory conversionScreenComponentsFactory = new SwingConversionScreenComponentsFactory();
        SwingConversionScreenAssembler conversionScreenAssembler = new DefaultSwingConversionScreenAssembler(conversionScreenComponentsFactory.createComponents());
        ConversionScreenManager conversionScreenManager = new DefaultSwingConversionScreenManager(conversionScreenAssembler);

        AudioPlayer audioPlayer = new JSoundAudioAdapter(
                new DefaultSequenceBuilder(),
                new DefaultSequencePlayer(MidiSystem.getSequencer())
        );
        AudioPlayerService audioPlayerService = new DefaultAudioPlayerService(audioPlayer);

        ScreenFactory screenFactory = new DefaultScreenFactory(
                conversionService,
                conversionScreenManager,
                audioPlayerService
        );
        AppNavigator navigator = new DefaultAppNavigator(screenFactory);

        // subscribes listeners to their events
        eventBus.subscribe(
                ConversionCompletedEvent.class,
                new PlayerConversionCompletedListener(audioPlayerService)
        );
        eventBus.subscribe(
                ConversionCompletedEvent.class,
                new NavigationConversionCompletedListener(navigator)
        );

        navigator.startApp();

    }
}
