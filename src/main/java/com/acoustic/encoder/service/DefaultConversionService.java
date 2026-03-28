package com.acoustic.encoder.service;

import com.acoustic.encoder.model.MusicModel;
import com.acoustic.encoder.model.MusicalInstruction;
import com.acoustic.encoder.parser.InstructionParser;

import java.util.List;

public class DefaultConversionService implements ConversionService {

    private final InstructionParser parser;

    public DefaultConversionService(InstructionParser parser) {

        this.parser = parser;
    }

    public MusicModel textToMusic(String text, int instrument, int bpm, int defaultOctave, int volume) {

        List<MusicalInstruction> musicalInstructions = this.parser.parseText(text);

        MusicModel music = new MusicModel(musicalInstructions, defaultOctave, volume, instrument, bpm);

        for (MusicalInstruction musicalInstruction : musicalInstructions) {
            System.out.println(musicalInstruction);
        }

        return music;
    }
}
