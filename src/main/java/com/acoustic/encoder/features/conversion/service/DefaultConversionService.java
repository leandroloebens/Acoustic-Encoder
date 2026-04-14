package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.shared.event.DefaultEventBus;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.model.MusicConfig;
import com.acoustic.encoder.shared.model.MusicModel;
import com.acoustic.encoder.shared.model.MusicalInstruction;
import com.acoustic.encoder.features.conversion.parser.InstructionParser;

import java.util.List;

public class DefaultConversionService implements ConversionService {

    private final InstructionParser parser;

    private final EventBus eventBus;

    public DefaultConversionService(InstructionParser parser, EventBus eventBus) {

        this.parser = parser;
        this.eventBus = eventBus;
    }

    public MusicModel textToMusic(String text, MusicConfig config) {

        List<MusicalInstruction> musicalInstructions = this.parser.parseText(text);

        MusicModel music = new MusicModel(musicalInstructions, config);

        this.eventBus.publish(new ConversionCompletedEvent(music));

        // TESTE------------------------------
        for (MusicalInstruction musicalInstruction : musicalInstructions) {
            System.out.println(musicalInstruction);
        }
        //---------------------------------

        return music;
    }
}
