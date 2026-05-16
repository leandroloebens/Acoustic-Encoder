package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.shared.model.MusicalInstruction;
import com.acoustic.encoder.shared.model.Voice;
import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.model.VoiceList;

import java.util.List;
import java.util.Objects;

public class DefaultVoiceParser implements VoiceParser {

    private final InstructionParser instructionParser;
    private final VoiceConfigSelector voiceConfigSelector;

    public DefaultVoiceParser(InstructionParser instructionParser, VoiceConfigSelector voiceConfigSelector) {
        this.instructionParser = Objects.requireNonNull(
                instructionParser, "InstructionParser cannot be null!");
        this.voiceConfigSelector = Objects.requireNonNull(
                voiceConfigSelector, "VoiceConfigSelector cannot be null!");
    }

    @Override
    // Separates each line from the text to parse
    public VoiceList parseVoices(String text, List<VoiceConfig> configs) {
        Objects.requireNonNull(text, "Text cannot be null!");
        Objects.requireNonNull(configs, "VoiceConfigs cannot be null!");

        VoiceList voiceList = new VoiceList();

        int voiceIndex = 0;
        for (String voiceText : text.lines().toList()) {

            List<MusicalInstruction> musicalInstructions = instructionParser.parseText(voiceText);
            VoiceConfig config = voiceConfigSelector.selectConfig(configs, voiceIndex);

            // TESTE------------------------------ TODO limpar
            for (MusicalInstruction musicalInstruction : musicalInstructions) {
                System.out.println(musicalInstruction);
            }
            //---------------------------------

            voiceList.add(new Voice(musicalInstructions, config));

            voiceIndex++;
        }

        return voiceList;
    }

}
