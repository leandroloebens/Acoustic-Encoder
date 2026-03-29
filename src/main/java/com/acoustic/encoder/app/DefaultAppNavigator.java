package com.acoustic.encoder.app;

import com.acoustic.encoder.controller.AudioPlayerController;
import com.acoustic.encoder.controller.ConversionController;
import com.acoustic.encoder.controller.DefaultAudioPlayerController;
import com.acoustic.encoder.controller.DefaultConversionController;
import com.acoustic.encoder.gui.MainScreen;
import com.acoustic.encoder.gui.PlayerScreen;
import com.acoustic.encoder.model.MusicModel;
import com.acoustic.encoder.service.AudioPlayerService;
import com.acoustic.encoder.service.ConversionService;

import javax.sound.midi.InvalidMidiDataException;

public class DefaultAppNavigator implements AppNavigator{

    private final ConversionService conversionService;

    private final AudioPlayerService playerService;

    public DefaultAppNavigator(ConversionService conversionService, AudioPlayerService playerService) {

        this.conversionService = conversionService;
        this.playerService = playerService;
    }

    public void startApp() {

        ConversionController conversionController = new DefaultConversionController(conversionService, this);

        MainScreen mainScreen = new MainScreen(conversionController);

        mainScreen.startFrame();
    }

    public void displayPlayerScreen(MusicModel musicModel) throws IllegalArgumentException, InvalidMidiDataException {

        AudioPlayerController playerController = new DefaultAudioPlayerController(playerService, musicModel);

        PlayerScreen playerScreen = new PlayerScreen(playerController);

        playerScreen.startFrame();

    }
}
