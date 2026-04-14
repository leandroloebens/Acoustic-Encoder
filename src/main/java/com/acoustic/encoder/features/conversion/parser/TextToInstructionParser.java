package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.shared.model.MusicalInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextToInstructionParser implements InstructionParser {

    private final Map<String, MusicalInstruction> encoderMap;

    public TextToInstructionParser(Map<String, MusicalInstruction> encoderMap) {
        this.encoderMap = encoderMap;
    }

    public List<MusicalInstruction> parseText(String text) {

        List<MusicalInstruction> instructionList = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {

            MusicalInstruction instruction = this.encoderMap.getOrDefault(
                    text.substring(i, i + 1),
                    this.getDefaultInstruction()
            );

            instructionList.add(instruction);

        }

        return instructionList;
    }

    private MusicalInstruction getDefaultInstruction() {
        return this.encoderMap.get("default");
    }
}
