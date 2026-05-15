package com.acoustic.encoder.infrastructure.audio.midi.command.handlers;

import com.acoustic.encoder.infrastructure.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiDelayBeatsHandler implements MidiCommandHandler {

    @Override
    public TrackContext handle(Track track, TrackContext context, int beats) {

        if (beats < 0) throw new IllegalArgumentException("Beats value must be positive!");

        long tickDelay = (long) beats * context.settings().ppqResolution();

        long finalTick = context.state().tick() + tickDelay;

        return context.withTick(finalTick);
    }


}
