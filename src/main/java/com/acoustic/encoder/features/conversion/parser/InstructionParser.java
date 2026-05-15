package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.List;

public interface InstructionParser {

    List<MusicalInstruction> parseText(String text);

}
