package com.acoustic.encoder.infrastructure.audio.player.command.handlers;

import com.acoustic.encoder.infrastructure.audio.player.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;

public class MidiOffsetLocalBpmHandler implements MidiCommandHandler {

    @Override
    public TrackContext handle(Track track, TrackContext context, int bpmValue) {

        int newBpm = Math.max(context.state().localBpm() + bpmValue, 10);

        int newTickDuration = Math.max(1, determineTickDurationForNewBpm(context, newBpm));

        return context.withLocalBpm(newBpm).withNoteTickDuration(newTickDuration);
    }

    private static int determineTickDurationForNewBpm(TrackContext context, int newBpm) {
        return (int) Math.round(
                ((double) context.settings().defaultBpm() / newBpm)
                        * context.settings().baseNoteTickDuration()
        );
    }

}
