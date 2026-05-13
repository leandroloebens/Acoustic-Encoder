package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.DefaultMidiCommandRegistry;
import com.acoustic.encoder.shared.model.Voice;
import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.model.MusicalInstruction;

import javax.sound.midi.Track;

public class DefaultTrackWriter implements TrackWriter {

    private static final int NOTE_VELOCITY = 64;

    private final DefaultMidiCommandRegistry commandRegistry;

    public DefaultTrackWriter(DefaultMidiCommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public void writeTrack(Track track, Voice voice, int channel, int ppqResolution) {

        initializeTrack(track, voice.config(), channel);

        int noteTickDuration = (int) ((1.0f/2.0f)*ppqResolution);

        processInstructionsToTrack(track, voice, channel, ppqResolution, noteTickDuration);

    }

    public void writeInitTempoTrack(Track track, int bpm) {

        track.add(MidiUtils.createTempoChangeEvent(bpm, 0));
    }

    private void initializeTrack(Track track, VoiceConfig config, int channel)  {

        track.add(MidiUtils.createInstrumentChangeEvent(config.defaultMidiInstrument(), channel, 0));

        track.add(MidiUtils.createVolumeChangeEvent(config.defaultVolume(), channel, 0));
    }

    private void processInstructionsToTrack(Track track, Voice voice, int channel, int ppqResolution, int noteTickDuration) {

//        TrackSettings settings = new TrackSettings(
//                channel,
//                noteTickDuration,
//                voice.config().defaultMidiInstrument(),
//                voice.config().defaultOctave(),
//                NOTE_VELOCITY
//        );

        TrackContext trackContext = TrackContext.initialContext(
                voice.config(),
                noteTickDuration,
                channel,
                ppqResolution,
                NOTE_VELOCITY
        );

        for (MusicalInstruction instruction : voice.musicalInstructions()) {

            trackContext = commandRegistry.getHandler(instruction.command())
                    .handle(track, trackContext, instruction.parameter());

            trackContext = trackContext.withPreviousInstruction(instruction);
        }

    }

}
