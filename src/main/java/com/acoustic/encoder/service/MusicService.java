package com.acoustic.encoder.service;

import com.acoustic.encoder.audio.AudioPlayer;
import com.acoustic.encoder.parser.TextToInstructionParser;
import com.acoustic.encoder.parser.TextToInstructionParser;

public class MusicService {

    TextToInstructionParser parser;

    AudioPlayer player;

    public MusicService(TextToInstructionParser parser, AudioPlayer player) {

        this.parser = parser;
        this.player = player;
    }

    public void textToMusic(String text, int instrument, int bpm, int deufaultOctave, int volume) {




    }
}
