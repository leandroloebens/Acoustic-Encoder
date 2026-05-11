package com.acoustic.encoder.features.player.audio.midi.track;

public record TrackState(
        //MusicalCommand previousCommand,
        int bpm,
        int noteTickDuration,
        int tick,
        int instrument,
        int octave,
        int volume
) {

    TrackState withTick(int newTick) {
        return new TrackState(bpm, noteTickDuration, newTick, instrument, octave, volume);
    }

    TrackState withInstrument(int newInstrument) {
        return new TrackState(bpm, noteTickDuration, tick, newInstrument, octave, volume);
    }

    TrackState withOctave(int newOctave) {
        return new TrackState(bpm, noteTickDuration, tick, instrument, newOctave, volume);
    }

    TrackState withVolume(int newVolume) {
        return new TrackState(bpm, noteTickDuration, tick, instrument, octave, newVolume);
    }
}
