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

    // Usamos instâncias REAIS de Enums e Records para representar os dados.
    // Escolhemos o NULL_COMMAND como nossa instrução padrão de teste.
    private final MusicalInstruction defaultInstruction = new MusicalInstruction(MusicalCommand.NULL_COMMAND, 0);

    @Test
    public void shouldThrowExceptionWhenConfigIsNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new DefaultInstructionParser(null);
        });
        assertEquals("Parser config cannot be null!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTextIsNull() {
        ParserConfig config = new ParserConfig(List.of(), defaultInstruction);
        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            parser.parseText(null);
        });
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
        // Criamos uma configuração real, sem nenhuma TokenRule
        ParserConfig config = new ParserConfig(List.of(), defaultInstruction);

        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        List<MusicalInstruction> result = parser.parseText("AB");

        assertEquals(2, result.size(), "Should return one instruction for each character");

        // Verifica se ambas as letras retornaram nossa instrução base de NULL_COMMAND
        assertEquals(defaultInstruction, result.get(0));
        assertEquals(defaultInstruction, result.get(1));
    }

    @Test
    public void shouldPrioritizeRuleThatConsumesMoreCharacters(
            @Mock TokenRule shortRule,
            @Mock TokenRule longRule) {

        // 1. Setup: Criamos instruções reais usando comandos diferentes do seu Enum
        MusicalInstruction shortInstruction = new MusicalInstruction(MusicalCommand.PLAY_NOTE, 1);
        MusicalInstruction longInstruction = new MusicalInstruction(MusicalCommand.CHANGE_INSTRUMENT, 2);

        ParserConfig config = new ParserConfig(List.of(shortRule, longRule), defaultInstruction);

        // 2. Programando o comportamento do nosso mock de TokenRule
        // Simulando a leitura do índice 0:
        when(shortRule.match("ABC", 0))
                .thenReturn(Optional.of(new TokenMatch(shortInstruction, 1)));
        when(longRule.match("ABC", 0))
                .thenReturn(Optional.of(new TokenMatch(longInstruction, 2)));

        // Simulando a leitura do índice 2 (após a regra longa consumir "AB"):
        when(shortRule.match("ABC", 2)).thenReturn(Optional.empty());
        when(longRule.match("ABC", 2)).thenReturn(Optional.empty());

        DefaultInstructionParser parser = new DefaultInstructionParser(config);

        // 3. Ação
        List<MusicalInstruction> result = parser.parseText("ABC");

        // 4. Verificação
        assertEquals(2, result.size(), "Should return 2 commands: the long rule (size 2) and the default (size 1)");

        // O algoritmo deve selecionar a instrução que usou CHANGE_INSTRUMENT (regra longa)
        assertEquals(longInstruction, result.get(0), "The rule with the highest character consumption should win");

        // O último caractere "C" deve cair na instrução padrão (NULL_COMMAND)
        assertEquals(defaultInstruction, result.get(1), "The unmatched character should fall back to default");
    }
}