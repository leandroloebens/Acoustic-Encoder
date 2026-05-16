package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;
import com.acoustic.encoder.features.player.model.MusicalNote;

import javax.sound.midi.Track;

public class MidiPlayNoteHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int parameter) {
        MusicalNote note = new MusicalNote(parameter, context.state().octave(), context.settings().noteVelocity());

        // Note ON
        track.add(MidiUtils.createNoteOnEvent(note, context.settings().channel(), context.state().tick()));

        // Note OFF
        track.add(MidiUtils.createNoteOffEvent(note, context.settings().channel(), context.state().tick() + context.state().noteTickDuration()));

        return context.withTick(context.state().tick() + context.state().noteTickDuration());
    }


}
