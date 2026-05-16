package com.acoustic.encoder.features.conversion.parser.config;

import com.acoustic.encoder.features.conversion.parser.rules.DelayTokenRule;
import com.acoustic.encoder.features.conversion.parser.rules.LiteralTokenRule;
import com.acoustic.encoder.features.conversion.parser.rules.TokenRule;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DefaultParserConfigFactory {

    private final static String DEFAULT_INSTRUCTION_TOKEN = "default";

    public ParserConfig create(Map<String, MusicalInstruction> encoderMap) {

        MusicalInstruction defaultInstruction = Objects.requireNonNull(
                encoderMap.get(DEFAULT_INSTRUCTION_TOKEN),
                "Default instruction not found!");

        List<TokenRule> tokenRules = new ArrayList<>();

        addLiteralRules(tokenRules, encoderMap);
        addGenericRules(tokenRules);

        return new ParserConfig(tokenRules, defaultInstruction);
    }

    private void addGenericRules(List<TokenRule> tokenRules) {
        tokenRules.add(new DelayTokenRule());
    }

    private void addLiteralRules(List<TokenRule> tokenRules, Map<String, MusicalInstruction> encoderMap) {

        encoderMap.entrySet().stream()
                .filter(entry -> !DEFAULT_INSTRUCTION_TOKEN.equals(entry.getKey()))
                .forEach(entry -> tokenRules.add(
                        new LiteralTokenRule(entry.getKey(), entry.getValue())
                ));
    }

}
