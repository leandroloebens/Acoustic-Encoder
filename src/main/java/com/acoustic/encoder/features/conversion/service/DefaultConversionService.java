package com.acoustic.encoder.features.conversion.service;

import com.acoustic.encoder.features.conversion.event.ConversionCompletedEvent;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.model.*;
import com.acoustic.encoder.features.conversion.parser.InstructionParser;

import java.util.List;

public class DefaultConversionService implements ConversionService {

    private final InstructionParser parser;

    private final EventBus eventBus;

    public DefaultConversionService(InstructionParser parser, EventBus eventBus) {

        this.parser = parser;
        this.eventBus = eventBus;
    }

    public MusicModel textToMusic(String text, List<VoiceConfig> configList) {

        List<MusicalInstruction> musicalInstructions = this.parser.parseText(text);

        VoiceList voiceList = new VoiceList();
        for (VoiceConfig config : configList) {
            Voice voice = new Voice(musicalInstructions, config);
            voiceList.add(voice);
        }

        int defaultBpm = configList.getFirst().defaultBpm();
        MusicModel music = new MusicModel(voiceList, defaultBpm);

        this.eventBus.publish(new ConversionCompletedEvent(music));

        // TESTE------------------------------
        for (MusicalInstruction musicalInstruction : musicalInstructions) {
            System.out.println(musicalInstruction);
        }
        //---------------------------------

        return music;
    }
}
