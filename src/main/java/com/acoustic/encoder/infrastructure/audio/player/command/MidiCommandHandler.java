package com.acoustic.encoder.infrastructure.audio.player.command;

import com.acoustic.encoder.infrastructure.audio.player.track.TrackContext;

import javax.sound.midi.Track;

public interface MidiCommandHandler {

    TrackContext handle(Track track, TrackContext context, int parameter);
}
