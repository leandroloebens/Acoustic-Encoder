package com.acoustic.encoder.infrastructure.audio.midi.track;

import com.acoustic.encoder.domain.music.MusicalInstruction;

public record TrackState(

        MusicalInstruction previousInstruction,
        int localBpm,
        int noteTickDuration,
        long tick,
        int instrument,
        int octave,
        int volume

) {

    TrackState withNoteTickDuration(int newNoteTickDuration) {
        return new TrackState(previousInstruction, localBpm, newNoteTickDuration, tick, instrument, octave, volume);
    }

    TrackState withTick(long newTick) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, newTick, instrument, octave, volume);
    }

    TrackState withInstrument(int newInstrument) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, tick, newInstrument, octave, volume);
    }

    TrackState withOctave(int newOctave) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, tick, instrument, newOctave, volume);
    }

    TrackState withVolume(int newVolume) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, tick, instrument, octave, newVolume);
    }

    TrackState withPreviousInstruction(MusicalInstruction newPreviousInstruction) {
        return new TrackState(newPreviousInstruction, localBpm, noteTickDuration, tick, instrument, octave, volume);
    }

    TrackState withLocalBpm(int newLocalBpm) {
        return new TrackState(previousInstruction, newLocalBpm, noteTickDuration, tick, instrument, octave, volume);
    }
}

