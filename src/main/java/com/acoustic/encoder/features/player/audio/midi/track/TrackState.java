package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.features.player.model.MusicalNote;
import com.acoustic.encoder.shared.model.MusicalInstruction;

public record TrackState(

        MusicalInstruction previousInstruction,
        int bpm,
        int noteTickDuration,
        int tick,
        int instrument,
        int octave,
        int volume

) {

    TrackState withTick(int newTick) {
        return new TrackState(previousInstruction, bpm, noteTickDuration, newTick, instrument, octave, volume);
    }

    TrackState withInstrument(int newInstrument) {
        return new TrackState(previousInstruction, bpm, noteTickDuration, tick, newInstrument, octave, volume);
    }

    TrackState withOctave(int newOctave) {
        return new TrackState(previousInstruction, bpm, noteTickDuration, tick, instrument, newOctave, volume);
    }

    TrackState withVolume(int newVolume) {
        return new TrackState(previousInstruction, bpm, noteTickDuration, tick, instrument, octave, newVolume);
    }

    TrackState withPreviousInstruction(MusicalInstruction newPreviousInstruction) {
        return new TrackState(newPreviousInstruction, bpm, noteTickDuration, tick, instrument, octave, volume);
    }
}
