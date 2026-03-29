package com.acoustic.encoder.parser;

import com.acoustic.encoder.model.MusicalInstruction;

import java.util.List;

public interface InstructionParser {

    List<MusicalInstruction> parseText(String text);

}
