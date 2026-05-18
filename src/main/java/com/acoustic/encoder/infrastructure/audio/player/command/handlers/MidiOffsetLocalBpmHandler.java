package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;
import java.util.Objects;

public class MidiOffsetLocalBpmHandler implements MidiCommandHandler {

    @Override
    public TrackContext handle(Track track, TrackContext context, int bpmValue) {
        Objects.requireNonNull(track, "Track cannot be null");
        Objects.requireNonNull(context, "TrackContext cannot be null");

        int newBpmValue = context.state().localBpm().value() + bpmValue;

        if (newBpmValue < Bpm.MIN_BPM) {
            newBpmValue = Bpm.MIN_BPM;
        }
        else if (newBpmValue > Bpm.MAX_BPM) {
            newBpmValue = Bpm.MAX_BPM;
        }

        int newTickDuration = Math.max(1, determineTickDurationForNewBpm(context, newBpmValue));

        return context.withLocalBpm(new Bpm(newBpmValue)).withNoteTickDuration(newTickDuration);
    }

    private static int determineTickDurationForNewBpm(TrackContext context, int newBpm) {
        return (int) Math.round(
                ((double) context.settings().defaultBpm().value() / newBpm)
                        * context.settings().baseNoteTickDuration()
        );
    }

}
