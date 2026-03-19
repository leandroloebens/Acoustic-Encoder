package com.acoustic.encoder.controller;

import com.acoustic.encoder.service.MusicService;

public class AppController {

    private final MusicService musicService;

    public AppController(MusicService musicService) {
        this.musicService = musicService;
    }

    public void onConvertButtonClick(String inputText) {

        if (inputText.isEmpty()) throw new IllegalArgumentException("Input text cannot be empty!");

        this.musicService.textToMusic(inputText);

    }
}
