package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.infrastructure.audio.player.MidiUtils;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;

public class MidiMultiplyVolumeHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int factor) {

        int newVolumeValue = context.state().volume().value() * factor;

        if (newVolumeValue < Volume.MIN_VOLUME) {
            newVolumeValue = Volume.MIN_VOLUME;
        }
        else if (newVolumeValue > Volume.MAX_VOLUME) {
            newVolumeValue = Volume.MAX_VOLUME;
        }

        TrackContext newContext = context.withVolume(new Volume(newVolumeValue));

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
