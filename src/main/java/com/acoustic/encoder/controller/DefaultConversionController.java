package com.acoustic.encoder.controller;

import com.acoustic.encoder.app.AppNavigator;
import com.acoustic.encoder.model.MusicConfig;
import com.acoustic.encoder.model.MusicModel;
import com.acoustic.encoder.model.UserConversionInput;
import com.acoustic.encoder.service.ConversionService;

import javax.sound.midi.InvalidMidiDataException;
import java.util.Objects;

public class DefaultConversionController implements ConversionController {

    private final ConversionService conversionService;

    private final AppNavigator navigator;

    public DefaultConversionController(ConversionService conversionService, AppNavigator navigator) {

        this.conversionService = conversionService;
        this.navigator = navigator;
    }

    public void handleConvertAction(UserConversionInput input) {

        Objects.requireNonNull(input, "UserInput cannot be null!");

        MusicModel music = this.conversionService.textToMusic(
                input.text(),
                new MusicConfig(
                        input.defaultMidiInstrument(),
                        input.bpm(),
                        input.defaultOctave(),
                        input.defaultVolume()
                )
        );

        try {
            navigator.displayPlayerScreen(music);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        System.out.println(music);

    }
}
