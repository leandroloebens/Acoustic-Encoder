package com.acoustic.encoder.infrastructure.audio.midi.command.handlers;

import com.acoustic.encoder.infrastructure.audio.midi.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiChangeInstrumentHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int newInstrument) {

        if (newInstrument < MidiUtils.INSTRUMENT_MIN || newInstrument > MidiUtils.INSTRUMENT_MAX)
            throw new IllegalArgumentException("Instrument out of range!");

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
