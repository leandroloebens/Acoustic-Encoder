package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiDelayBeatsHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int beats) {

        int tickDelay = beats*context.settings().ppqResolution();

        return context.withDelay(tickDelay);
    }


}
