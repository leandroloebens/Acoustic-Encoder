package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.infrastructure.audio.player.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;

public class MidiOffsetOctaveHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int incVal) {

        int newOctave = context.state().octave() + incVal;

        if (newOctave < MidiUtils.OCTAVE_MIN || newOctave > MidiUtils.OCTAVE_MAX)
            newOctave = context.settings().defaultOctave();

        return context.withOctave(newOctave);
    }
}
