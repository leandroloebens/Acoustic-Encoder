package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;
import com.acoustic.encoder.features.player.model.MusicalNote;
import com.acoustic.encoder.shared.model.Voice;
import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.model.MusicalCommand;
import com.acoustic.encoder.shared.model.MusicalInstruction;

import javax.sound.midi.Track;
import java.util.List;

public class DefaultTrackWriter implements TrackWriter {

    final static int NOTE_VELOCITY = 64;

    public void writeTrack(
            Track track,
            Voice voice,
            int channel,
            int ppqResolution
    ) {

        initializeTrack(track, voice.config(), channel);

        int noteTickDuration = (int) ((1.0f/2.0f)*ppqResolution);

        processInstructionsToTrack(track, voice, channel, noteTickDuration);
        
    }

    public void writeInitTempoTrack(Track track, int bpm) {

        track.add(MidiUtils.createTempoChangeEvent(bpm, 0));
    }

    private void initializeTrack(Track track, VoiceConfig config, int channel)  {

        track.add(MidiUtils.createInstrumentChangeEvent(config.defaultMidiInstrument(), channel, 0));

        track.add(MidiUtils.createVolumeChangeEvent(config.defaultVolume(), channel, 0));
    }

    private void processInstructionsToTrack(
            Track track,
            Voice voice,
            int channel,
            int noteTickDuration
    ) {

        TrackContext trackContext = TrackContext.initialContext(voice.config(),noteTickDuration, channel);

        for (int i = 0; i < voice.musicalInstructions().size(); i++) {

            MusicalInstruction instruction = voice.musicalInstructions().get(i);

            trackContext = switch (instruction.command()) {
                case PLAY_NOTE -> handlePlayNote(track, trackContext, instruction.parameter(), channel, noteTickDuration);
                case SILENCE -> handleSilence(trackContext, noteTickDuration);
                case DOUBLE_VOLUME -> handleDoubleVolume(track, trackContext, channel);
                case CHANGE_INSTRUMENT -> handleChangeInstrument(track, trackContext, instruction.parameter(), channel);
                case INCREMENT_INSTRUMENT -> handleIncrementInstrument(track, trackContext, instruction.parameter(), channel);
                case INCREMENT_OCTAVE -> handleIncrementOctave(trackContext, instruction.parameter());
                case PLAY_PREVIOUS -> handlePlayPreviousNote(track, trackContext, voice.musicalInstructions(), i, channel, noteTickDuration);
                case NULL_COMMAND -> trackContext;
            };
        }
    }

    private TrackContext handlePlayNote(
            Track track,
            TrackContext context,
            int parameter,
            int channel,
            int noteTickDuration
    ) {

        MusicalNote note = new MusicalNote(parameter, context.state().octave(), NOTE_VELOCITY);

        // Note ON
        track.add(MidiUtils.createNoteOnEvent(note, channel, context.state().tick()));

        // Note OFF
        track.add(MidiUtils.createNoteOffEvent(note, channel, context.state().tick() + noteTickDuration));

        return context.withTick(context.state().tick() + noteTickDuration);
    }

    private TrackContext handleSilence(TrackContext context, int noteTickDuration) {

        return context.withTick(context.state().tick() + noteTickDuration);
    }

    private TrackContext handleDoubleVolume(Track track, TrackContext context, int channel) {

        TrackContext newContext = context.doubleVolume();

        track.add(MidiUtils.createVolumeChangeEvent(newContext.state().volume(), channel, newContext.state().tick()));

        return newContext;
    }

    private TrackContext handleChangeInstrument(
            Track track,
            TrackContext context,
            int newInstrument,
            int channel
    ) {

        TrackContext newContext = context.withInstrument(newInstrument);

        track.add(
                MidiUtils.createInstrumentChangeEvent(
                        newContext.state().instrument(), channel, newContext.state().tick()
                )
        );

        return newContext;
    }

    private TrackContext handleIncrementInstrument(
            Track track,
            TrackContext context,
            int incVal,
            int channel
    ) {

        TrackContext newContext = context.incrementInstrument(incVal);

        track.add(MidiUtils.createInstrumentChangeEvent(newContext.state().instrument(), channel, newContext.state().tick()));

        return newContext;
    }

    private TrackContext handleIncrementOctave(TrackContext context, int incVal) {

        return context.incrementOctave(incVal);
    }

    private TrackContext handlePlayPreviousNote(
            Track track,
            TrackContext context,
            List<MusicalInstruction> musicalInstructions,
            int currentIndex,
            int channel,
            int noteTickDuration
    ) {

        if (isPreviousInstructionPlayNote(musicalInstructions, currentIndex)) {
            return handlePlayNote(track, context, musicalInstructions.get(currentIndex - 1).parameter(), channel, noteTickDuration);
        }

        return handleSilence(context, noteTickDuration);
    }

    private boolean isPreviousInstructionPlayNote(List<MusicalInstruction> musicalInstructions, int currentIndex) {

        if (currentIndex == 0) return false;
        return musicalInstructions.get(currentIndex - 1).command() == MusicalCommand.PLAY_NOTE;
    }
}
