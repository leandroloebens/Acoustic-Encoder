package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;
import java.util.Objects;

public class MidiOffsetOctaveHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int incVal) {
        Objects.requireNonNull(track, "Track cannot be null");
        Objects.requireNonNull(context, "TrackContext cannot be null");

        Octave newOctave;

        try {
            newOctave = new Octave(context.state().octave().value() + incVal);
        }
        catch (IllegalArgumentException e) {
            // If the new octave is out of bounds, reset to default
            newOctave = context.settings().defaultOctave();
        }

        return context.withOctave(newOctave);
    }
}
