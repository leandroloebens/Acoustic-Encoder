package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiSilenceHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int parameter) {

        return context.withTick(context.state().tick() + context.state().noteTickDuration());
    }

}
