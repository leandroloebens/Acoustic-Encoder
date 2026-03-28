package com.acoustic.encoder.controller;

import com.acoustic.encoder.model.MusicConfig;
import com.acoustic.encoder.model.MusicModel;
import com.acoustic.encoder.model.UserConversionInput;
import com.acoustic.encoder.service.ConversionService;

import java.util.Objects;

public class DefaultConversionController implements ConversionController {

    private final ConversionService conversionService;

    public DefaultConversionController(ConversionService conversionService) {
        this.conversionService = conversionService;
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

        System.out.println(music);

    }
}
