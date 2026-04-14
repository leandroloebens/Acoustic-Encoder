package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.shared.model.MusicalInstruction;

import java.util.List;

public interface InstructionParser {

    List<MusicalInstruction> parseText(String text);

}
