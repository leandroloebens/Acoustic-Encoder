package com.acoustic.encoder.audio.midi;

import com.acoustic.encoder.model.*;

import javax.sound.midi.*;
import java.util.List;

public class DefaultSequenceBuilder implements SequenceBuilder {

    private final static float DIVISION_TYPE = Sequence.PPQ;
    private final static int PPQ_RESOLUTION = 480;
    private final static int DEFAULT_CHANNEL = 0;
    private final static int NOTE_VELOCITY = 64;
    private final static int NOTE_TICK_DURATION = (int) ((1.0f/2.0f)*PPQ_RESOLUTION);

    public Sequence buildSequence(MusicModel musicModel) throws InvalidMidiDataException {

        Sequence sequence = new Sequence(DIVISION_TYPE, PPQ_RESOLUTION);
        Track mainTrack = sequence.createTrack();

        initializeTrack(mainTrack, musicModel.config());
        processInstructionsToTrack(mainTrack, musicModel.musicalInstructions(), musicModel.config());

        return sequence;
    }

    private void initializeTrack(Track track, MusicConfig config) throws InvalidMidiDataException {

        track.add(new MidiEvent(
                MidiUtils.createInstrumentChange(config.defaultMidiInstrument(), DEFAULT_CHANNEL),
                0
        ));

        track.add(new MidiEvent(
                MidiUtils.createVolumeChange(config.defaultVolume(), DEFAULT_CHANNEL),
                0
        ));
    }

    private void processInstructionsToTrack(
            Track track,
            List<MusicalInstruction> musicalInstructions,
            MusicConfig config
    ) throws InvalidMidiDataException {

        TrackContext trackContext = TrackContext.initialContext(config);

        for (int i = 0; i < musicalInstructions.size(); i++) {

            MusicalInstruction instruction = musicalInstructions.get(i);

            trackContext = switch (instruction.command()) {
                case PLAY_NOTE -> handlePlayNote(track, trackContext, instruction.parameter());
                case SILENCE -> handleSilence(trackContext);
                case DOUBLE_VOLUME -> handleDoubleVolume(track, trackContext);
                case CHANGE_INSTRUMENT -> handleChangeInstrument(track, trackContext, instruction.parameter());
                case INCREMENT_INSTRUMENT -> handleIncrementInstrument(track, trackContext, instruction.parameter());
                case INCREMENT_OCTAVE -> handleIncrementOctave(trackContext, instruction.parameter());
                case PLAY_PREVIOUS -> handlePlayPreviousNote(track, trackContext, musicalInstructions, i);
                case NULL_COMMAND -> trackContext;
            };
        }
    }

    private TrackContext handlePlayNote(
            Track track,
            TrackContext context,
            int parameter
    ) throws InvalidMidiDataException {

        MusicalNote note = createNote(parameter, context.octave());

        // Note ON
        track.add(new MidiEvent(
                MidiUtils.createNoteOn(note, DEFAULT_CHANNEL),
                context.tick())
        );

        // Note OFF
        track.add(new MidiEvent(
                MidiUtils.createNoteOff(note, DEFAULT_CHANNEL),
                context.tick() + NOTE_TICK_DURATION)
        );

        return context.withTick(context.tick() + NOTE_TICK_DURATION);
    }

    private TrackContext handleSilence(TrackContext context) {

        return context.withTick(context.tick() + NOTE_TICK_DURATION);
    }

    private TrackContext handleDoubleVolume(Track track, TrackContext context) throws InvalidMidiDataException {

        TrackContext newContext = context.doubleVolume();

        track.add(new MidiEvent(
                MidiUtils.createVolumeChange(newContext.volume(), DEFAULT_CHANNEL),
                newContext.tick()
        ));

        return newContext;
    }

    private TrackContext handleChangeInstrument(
            Track track,
            TrackContext context,
            int newInstrument
    ) throws InvalidMidiDataException {

        TrackContext newContext = context.withInstrument(newInstrument);

        track.add(new MidiEvent(
                MidiUtils.createInstrumentChange(newContext.instrument(), DEFAULT_CHANNEL),
                newContext.tick()
        ));

        return newContext;
    }

    private TrackContext handleIncrementInstrument(
            Track track,
            TrackContext context,
            int incVal
    ) throws InvalidMidiDataException {

        TrackContext newContext = context.incrementInstrument(incVal);

        track.add(new MidiEvent(
                MidiUtils.createInstrumentChange(newContext.instrument(), DEFAULT_CHANNEL),
                newContext.tick()
        ));

        return newContext;
    }

    private TrackContext handleIncrementOctave(TrackContext context, int incVal) {

        return context.incrementOctave(incVal);
    }

    private TrackContext handlePlayPreviousNote(
            Track track,
            TrackContext context,
            List<MusicalInstruction> musicalInstructions,
            int currentIndex
    ) throws InvalidMidiDataException {

        if (isPreviousInstructionPlayNote(musicalInstructions, currentIndex)) {
            return handlePlayNote(track, context, musicalInstructions.get(currentIndex - 1).parameter());
        }

        return handleSilence(context);
    }

    private boolean isPreviousInstructionPlayNote(List<MusicalInstruction> musicalInstructions, int currentIndex) {

        if (currentIndex == 0) return false;
        return musicalInstructions.get(currentIndex - 1).command() == MusicalCommand.PLAY_NOTE;
    }

    private MusicalNote createNote(int pitch, int octave) {

        return new MusicalNote(Pitch.fromValue(pitch), octave, NOTE_VELOCITY);
    }

}
