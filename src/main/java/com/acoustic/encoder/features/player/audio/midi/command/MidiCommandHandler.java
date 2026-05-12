package com.acoustic.encoder.features.player.audio.midi.command;

import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public interface MidiCommandHandler {

    TrackContext handle(Track track, TrackContext context, int parameter);
}
