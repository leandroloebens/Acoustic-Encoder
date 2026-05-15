package com.acoustic.encoder.infrastructure.audio.midi.command;

import com.acoustic.encoder.infrastructure.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public interface MidiCommandHandler {

    TrackContext handle(Track track, TrackContext context, int parameter);
}
