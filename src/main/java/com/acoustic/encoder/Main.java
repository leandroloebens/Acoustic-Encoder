package com.acoustic.encoder;

import com.acoustic.encoder.app.AppNavigator;
import com.acoustic.encoder.app.DefaultAppNavigator;
import com.acoustic.encoder.audio.*;
import com.acoustic.encoder.config.ConfigLoader;
import com.acoustic.encoder.controller.DefaultConversionController;
import com.acoustic.encoder.controller.ConversionController;
import com.acoustic.encoder.gui.MainScreen;
import com.acoustic.encoder.parser.InstructionParser;
import com.acoustic.encoder.parser.TextToInstructionParser;
import com.acoustic.encoder.service.AudioPlayerService;
import com.acoustic.encoder.service.DefaultAudioPlayerService;
import com.acoustic.encoder.service.DefaultConversionService;
import com.acoustic.encoder.service.ConversionService;

import javax.sound.midi.MidiSystem;

public class Main {

    void main() throws Exception {

        ConfigLoader configLoader = new ConfigLoader(ConfigLoader.CONFIG_FILE_NAME);
        InstructionParser parser = new TextToInstructionParser(configLoader.loadConfigMap());
        ConversionService conversionService = new DefaultConversionService(parser);

        AudioPlayer audioPlayer = new JSoundAudioAdapter(
                new DefaultSequenceBuilder(),
                new DefaultSequencePlayer(MidiSystem.getSequencer())
        );
        AudioPlayerService audioPlayerService = new DefaultAudioPlayerService(audioPlayer);

        AppNavigator navigator = new DefaultAppNavigator(conversionService, audioPlayerService);

        navigator.startApp();
//
//        ConversionController conversionController = new DefaultConversionController(conversionService);
//
//        MainScreen mainScreen = new MainScreen(conversionController);
//
//        mainScreen.startFrame();

        }
}
