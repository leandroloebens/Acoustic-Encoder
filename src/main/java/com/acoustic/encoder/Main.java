package com.acoustic.encoder;

import com.acoustic.encoder.audio.AudioOutput;
import com.acoustic.encoder.audio.MidiPlayer;
import com.acoustic.encoder.config.ConfigLoader;
import com.acoustic.encoder.controller.DefaultConversionController;
import com.acoustic.encoder.controller.ConversionController;
import com.acoustic.encoder.gui.MainScreen;
import com.acoustic.encoder.parser.InstructionParser;
import com.acoustic.encoder.parser.TextToInstructionParser;
import com.acoustic.encoder.service.DefaultConversionService;
import com.acoustic.encoder.service.ConversionService;

public class Main {

    void main() throws Exception {

        ConfigLoader configLoader = new ConfigLoader(ConfigLoader.CONFIG_FILE_NAME);

        InstructionParser parser = new TextToInstructionParser(configLoader.loadConfigMap());

        AudioOutput player = new MidiPlayer();

        ConversionService musicService = new DefaultConversionService(parser);

        ConversionController controller = new DefaultConversionController(musicService);

        MainScreen mainScreen = new MainScreen(controller);

        mainScreen.startFrame();

        }
}
