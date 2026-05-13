package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiOffsetLocalBpmHandler implements MidiCommandHandler {

    @Override
    public TrackContext handle(Track track, TrackContext context, int bpmValue) {

        int newBpm = Math.max(context.state().localBpm() + bpmValue, 10);

        int newTickDuration = determineTickDurationForNewBpm(context, newBpm);

        return context.withLocalBpm(newBpm).withNoteTickDuration(newTickDuration);
    }

    private static int determineTickDurationForNewBpm(TrackContext context, int newBpm) {
        return (int) Math.round(
                ((double) context.state().localBpm() / newBpm)
                        * context.state().noteTickDuration()
        );
    }

}
