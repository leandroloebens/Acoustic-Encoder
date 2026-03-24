package com.acoustic.encoder.controller;

import com.acoustic.encoder.service.MusicUseCase;

public class AppController implements ConvertTextController {

    private final MusicUseCase musicUseCase;

    public AppController(MusicUseCase musicUseCase) {
        this.musicUseCase = musicUseCase;
    }

    public void onConvertButtonClick(String inputText) {

        if (inputText == null || inputText.isEmpty()) throw new IllegalArgumentException("Input text cannot be empty!");

        this.musicUseCase.textToMusic(inputText, 0, 120, 4, 100);

    }
}
