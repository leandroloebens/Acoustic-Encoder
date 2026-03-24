package com.acoustic.encoder;

import com.acoustic.encoder.audio.AudioOutput;
import com.acoustic.encoder.audio.AudioPlayer;
import com.acoustic.encoder.config.ConfigLoader;
import com.acoustic.encoder.controller.AppController;
import com.acoustic.encoder.controller.ConvertTextController;
import com.acoustic.encoder.gui.MainScreen;
import com.acoustic.encoder.parser.InstructionParser;
import com.acoustic.encoder.parser.TextToInstructionParser;
import com.acoustic.encoder.service.DefaultMusicService;
import com.acoustic.encoder.service.MusicUseCase;

public class Main {

    void main() throws Exception {

        ConfigLoader configLoader = new ConfigLoader(ConfigLoader.CONFIG_FILE_NAME);

        InstructionParser parser = new TextToInstructionParser(configLoader.loadConfigMap());

        AudioOutput player = new AudioPlayer();

        MusicUseCase musicService = new DefaultMusicService(parser, player);

        ConvertTextController controller = new AppController(musicService);

        MainScreen mainScreen = new MainScreen(controller);

        mainScreen.startFrame();

        }
}
