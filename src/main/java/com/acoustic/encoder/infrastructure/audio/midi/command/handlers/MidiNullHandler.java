package com.acoustic.encoder.infrastructure.audio.midi.command.handlers;

import com.acoustic.encoder.infrastructure.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.infrastructure.audio.midi.track.TrackContext;

import javax.sound.midi.Track;

public class MidiNullHandler implements MidiCommandHandler {

    public TrackContext handle(Track track, TrackContext context, int parameter) {

        return context;
    }
}
