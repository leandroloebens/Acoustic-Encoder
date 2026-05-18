package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.infrastructure.audio.player.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;

public class MidiChangeInstrumentHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int newInstrumentVal) {

        InstrumentId newInstrument = new InstrumentId(newInstrumentVal);

        TrackContext newContext = context.withInstrument(newInstrument);

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
