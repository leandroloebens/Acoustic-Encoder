package com.acoustic.encoder.infrastructure.audio.player.track;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.infrastructure.audio.player.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.player.command.DefaultMidiCommandRegistry;
import com.acoustic.encoder.domain.voice.Voice;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import javax.sound.midi.Track;

public class DefaultTrackWriter implements TrackWriter {

    private static final int NOTE_VELOCITY = 64;

    private final DefaultMidiCommandRegistry commandRegistry;

    public DefaultTrackWriter(DefaultMidiCommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void writeTrack(Track track, Voice voice, Bpm initialBpm, int channel, int ppqResolution) {

        initializeTrack(track, voice.config(), channel);

        // TODO fix magic number
        int noteTickDuration = (int) ((1.0f/2.0f)*ppqResolution);

        processInstructionsToTrack(track, voice, channel, ppqResolution, noteTickDuration, initialBpm);

    }

    public void writeInitTempoTrack(Track track, Bpm bpm) {

        track.add(MidiUtils.createTempoChangeEvent(bpm, 0));
    }

    private void initializeTrack(Track track, VoiceConfig config, int channel)  {

        track.add(MidiUtils.createInstrumentChangeEvent(config.defaultInstrument(), channel, 0));

        track.add(MidiUtils.createVolumeChangeEvent(config.defaultVolume(), channel, 0));
    }

    private void processInstructionsToTrack(
            Track track, Voice voice, int channel, int ppqResolution, int noteTickDuration, Bpm initialBpm
    ) {


//        TrackSettings settings = new TrackSettings(
//                channel,
//                noteTickDuration,
//                voice.config().defaultInstrument(),
//                voice.config().defaultOctave(),
//                NOTE_VELOCITY
//        );
        // TODO refactor param / DefaultTrackContextFactory
        TrackContext trackContext = TrackContext.initialContext(
                voice.config(),
                noteTickDuration,
                channel,
                ppqResolution,
                NOTE_VELOCITY,
                initialBpm
        );

        for (MusicalInstruction instruction : voice.musicalInstructions()) {

            trackContext = commandRegistry.getHandler(instruction.command())
                    .handle(track, trackContext, instruction.parameter());

            trackContext = trackContext.withPreviousInstruction(instruction);
        }

    }

}
