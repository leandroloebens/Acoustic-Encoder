package com.acoustic.encoder.infrastructure.audio.player.track;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;

public record TrackSettings(
        int channel,
        int ppqResolution,
        InstrumentId defaultInstrument,
        Octave defaultOctave,
        int noteVelocity,
        Bpm defaultBpm,
        int baseNoteTickDuration
) {

    public TrackSettings {

        // TODO validate OTHERS INPUTS
        validateChannel(channel);

    }

    private static void validateChannel(int channel) {
        if (channel < 0 || channel > 15) {
            throw new IllegalArgumentException("Channel must be in range [0, 15]");
        }
    }

    private static void validatePpqResolution(int ppqResolution) {
        if (ppqResolution < 0 || ppqResolution > 10000) {
            throw new IllegalArgumentException("Invalid ppq resolution: " + ppqResolution);
        }
    }

}
