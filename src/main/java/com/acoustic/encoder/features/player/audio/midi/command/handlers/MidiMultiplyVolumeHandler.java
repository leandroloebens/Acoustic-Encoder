package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiMultiplyVolumeHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int factor) {

        int newVolume = context.state().volume() * factor;

        if (newVolume < MidiUtils.VOL_MIN || newVolume > MidiUtils.VOL_MAX)
            newVolume = MidiUtils.VOL_MAX;

        TrackContext newContext = context.withVolume(newVolume);

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
