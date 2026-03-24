package com.acoustic.encoder.service;

import com.acoustic.encoder.audio.AudioOutput;
import com.acoustic.encoder.model.MusicalInstruction;
import com.acoustic.encoder.parser.InstructionParser;

import java.util.List;

public class DefaultMusicService implements MusicUseCase {

    private final InstructionParser parser;

    private final AudioOutput player;

    public DefaultMusicService(InstructionParser parser, AudioOutput player) {

        this.parser = parser;
        this.player = player;
    }

    public void textToMusic(String text, int instrument, int bpm, int defaultOctave, int volume) {

        List<MusicalInstruction> musicalInstructions = this.parser.parseText(text);

        for (MusicalInstruction musicalInstruction : musicalInstructions) {
            System.out.println(musicalInstruction);
        }


    }
}
