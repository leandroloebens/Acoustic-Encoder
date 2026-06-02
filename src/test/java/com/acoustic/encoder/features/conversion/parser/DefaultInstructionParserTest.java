package com.acoustic.encoder.features.conversion.parser;

import com.acoustic.encoder.domain.music.MusicalCommand;
import com.acoustic.encoder.domain.music.MusicalInstruction;
import com.acoustic.encoder.features.conversion.parser.config.ParserConfig;
import com.acoustic.encoder.features.conversion.parser.rules.TokenRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultInstructionParserTest {

    // Mocks for dependencies
    private final MusicalInstruction defaultInstruction = new MusicalInstruction(MusicalCommand.NULL_COMMAND, 0);

    @Test
    public void shouldThrowExceptionWhenConfigIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> new DefaultInstructionParser(null));
        assertEquals("Parser config cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTextIsNull() {
        ParserConfig config = new ParserConfig(List.of(), defaultInstruction);
        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        Exception exception = assertThrows(NullPointerException.class, () -> parser.parseText(null));
        assertEquals("Text cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldReturnEmptyListWhenTextIsEmpty() {
        ParserConfig config = new ParserConfig(List.of(), defaultInstruction);
        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        List<MusicalInstruction> result = parser.parseText("");

        assertTrue(result.isEmpty(), "The instruction list should be empty for empty text");
    }

    @Test
    public void shouldReturnDefaultInstructionWhenNoRuleMatches() {
        // Mock config
        ParserConfig config = new ParserConfig(List.of(), defaultInstruction);

        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        List<MusicalInstruction> result = parser.parseText("AB");

        assertEquals(2, result.size(), "Should return one instruction for each character");

        // Verifies that the default instruction is returned for each character
        assertEquals(defaultInstruction, result.get(0));
        assertEquals(defaultInstruction, result.get(1));
    }

    @Test
    public void shouldPrioritizeRuleThatConsumesMoreCharacters(
            @Mock TokenRule shortRule,
            @Mock TokenRule longRule) {

        // 1. Setup: Creates real MusicalInstructions using different MusicalCommands from the Enum
        MusicalInstruction shortInstruction = new MusicalInstruction(MusicalCommand.PLAY_NOTE, 1);
        MusicalInstruction longInstruction = new MusicalInstruction(MusicalCommand.CHANGE_INSTRUMENT, 2);

        ParserConfig config = new ParserConfig(List.of(shortRule, longRule), defaultInstruction);

        // 2. Programming TokenRule mocks
        // Simulates index 0 reading
        when(shortRule.match("ABC", 0))
                .thenReturn(Optional.of(new TokenMatch(shortInstruction, 1)));
        when(longRule.match("ABC", 0))
                .thenReturn(Optional.of(new TokenMatch(longInstruction, 2)));

        // SSimulates index 2 reading (after long rule consumes "AB"):
        when(shortRule.match("ABC", 2)).thenReturn(Optional.empty());
        when(longRule.match("ABC", 2)).thenReturn(Optional.empty());

        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        // 3. Action
        List<MusicalInstruction> result = parser.parseText("ABC");

        // 4. Assertions
        assertEquals(2, result.size(), "Should return 2 commands: the long rule (size 2) and the default (size 1)");

        // Must select the rule that consumes more characters (CHANGE_INSTRUMENT)
        assertEquals(longInstruction, result.get(0), "The rule with the highest character consumption should win");

        // Last character 'C' should be handled by the default rule
        assertEquals(defaultInstruction, result.get(1), "The unmatched character should fall back to default");
    }
}