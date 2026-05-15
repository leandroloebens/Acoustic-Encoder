package com.acoustic.encoder.infrastructure.audio.midi.command.handlers;

import com.acoustic.encoder.infrastructure.audio.midi.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiOffsetInstrumentHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int incVal) {

        int newInstrument = context.state().instrument() + incVal;

        if (newInstrument < MidiUtils.INSTRUMENT_MIN || newInstrument > MidiUtils.INSTRUMENT_MAX)
            newInstrument = context.settings().defaultInstrument();

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
