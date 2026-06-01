package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.features.conversion.parser.config.ParserConfig;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.*;

public class DefaultInstructionParser implements InstructionParser {

    private final ParserConfig config;

    public DefaultInstructionParser(ParserConfig config) {
        this.config = Objects.requireNonNull(config, "Parser config cannot be null!");
    }

    @Override
    public List<MusicalInstruction> parseText(String text) {

        Objects.requireNonNull(text, "Text cannot be null!");

        List<MusicalInstruction> instructionList = new ArrayList<>();

        int index = 0;
        while (index < text.length()) {

            TokenMatch match = findLongerMatch(text, index);

            instructionList.add(match.instruction());
            index += match.consumedChars();
        }

        return instructionList;
    }

    private TokenMatch findLongerMatch(String text, int index) {

        return config.tokenRules().stream()
                .map(rule -> rule.match(text, index))
                .flatMap(Optional::stream)
                .max(Comparator.comparingInt(TokenMatch::consumedChars))
                .orElse(new TokenMatch(config.defaultInstruction(), 1));

    }
}
