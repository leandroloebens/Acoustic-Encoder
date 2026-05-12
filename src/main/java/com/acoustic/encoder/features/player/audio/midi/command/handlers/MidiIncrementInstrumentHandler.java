package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiIncrementInstrumentHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int incVal) {

        TrackContext newContext = context.incrementInstrument(incVal);

        track.add(
                MidiUtils.createInstrumentChangeEvent(
                        newContext.state().instrument(),
                        newContext.settings().channel(),
                        newContext.state().tick()
                )
        );

        return newContext;
    }

}
