package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiOffsetOctaveHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int incVal) {

        int newOctave = context.state().octave() + incVal;

        if (newOctave < MidiUtils.OCTAVE_MIN || newOctave > MidiUtils.OCTAVE_MAX)
            newOctave = context.settings().defaultOctave();

        return context.withOctave(newOctave);
    }
}
