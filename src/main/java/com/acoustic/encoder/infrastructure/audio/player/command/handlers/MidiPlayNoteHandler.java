package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.infrastructure.audio.player.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;
import com.acoustic.encoder.domain.music.MusicalNote;

import javax.sound.midi.Track;
import java.util.Objects;

public class MidiPlayNoteHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int parameter) {
        Objects.requireNonNull(track, "Track cannot be null!");
        Objects.requireNonNull(context, "Track context cannot be null!");

        MusicalNote note = new MusicalNote(parameter, context.state().octave(), context.settings().noteVelocity());

        // Note ON
        track.add(MidiUtils.createNoteOnEvent(note, context.settings().channel(), context.state().tick()));

        // Note OFF
        track.add(MidiUtils.createNoteOffEvent(note, context.settings().channel(), context.state().tick() + context.state().noteTickDuration()));

        return context.withTick(context.state().tick() + context.state().noteTickDuration());
    }


}
