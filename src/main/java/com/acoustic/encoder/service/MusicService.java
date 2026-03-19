package com.acoustic.encoder.service;

import com.acoustic.encoder.audio.AudioPlayer;
import com.acoustic.encoder.parser.TextParser;

public class MusicService {

    TextParser parser;

    AudioPlayer player;

    public MusicService(TextParser parser, AudioPlayer player) {

        this.parser = parser;
        this.player = player;
    }

    public void textToMusic(String text) {




    }
}
