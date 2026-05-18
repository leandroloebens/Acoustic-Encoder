package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.infrastructure.audio.player.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;
import java.util.Objects;

public class MidiOffsetInstrumentHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int incVal) {
        Objects.requireNonNull(track, "Track cannot be null!");
        Objects.requireNonNull(context, "TrackContext cannot be null!");

        InstrumentId newInstrument;
        try {
            newInstrument = new InstrumentId(context.state().instrument().value() + incVal);
        }
        catch (IllegalArgumentException e) {
            newInstrument = context.settings().defaultInstrument();
        }

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
