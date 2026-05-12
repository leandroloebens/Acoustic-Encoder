package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiMultiplyVolumeHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int factor) {

        TrackContext newContext = context.multiplyVolumeBy(factor);

        track.add(
                MidiUtils.createVolumeChangeEvent(
                        newContext.state().volume(),
                        newContext.settings().channel(),
                        newContext.state().tick()
                )
        );

        return newContext;
    }

}
