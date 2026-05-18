package com.acoustic.encoder.infrastructure.audio.player.track;

import com.acoustic.encoder.domain.music.MusicalInstruction;
import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;

public record TrackState(

        MusicalInstruction previousInstruction,
        Bpm localBpm,
        int noteTickDuration,
        long tick,
        InstrumentId instrument,
        Octave octave,
        Volume volume

) {

    TrackState withNoteTickDuration(int newNoteTickDuration) {
        return new TrackState(previousInstruction, localBpm, newNoteTickDuration, tick, instrument, octave, volume);
    }

    TrackState withTick(long newTick) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, newTick, instrument, octave, volume);
    }

    TrackState withInstrument(InstrumentId newInstrument) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, tick, newInstrument, octave, volume);
    }

    TrackState withOctave(Octave newOctave) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, tick, instrument, newOctave, volume);
    }

    TrackState withVolume(Volume newVolume) {
        return new TrackState(previousInstruction, localBpm, noteTickDuration, tick, instrument, octave, newVolume);
    }

    TrackState withPreviousInstruction(MusicalInstruction newPreviousInstruction) {
        return new TrackState(newPreviousInstruction, localBpm, noteTickDuration, tick, instrument, octave, volume);
    }

    TrackState withLocalBpm(Bpm newLocalBpm) {
        return new TrackState(previousInstruction, newLocalBpm, noteTickDuration, tick, instrument, octave, volume);
    }
}

