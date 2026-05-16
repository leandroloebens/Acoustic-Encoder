package com.acoustic.encoder.infrastructure.audio.player.track;

public record TrackSettings(
        int channel,
        int ppqResolution,
        int defaultInstrument,
        int defaultOctave,
        int noteVelocity,
        int defaultBpm,
        int baseNoteTickDuration
) {

    public TrackSettings {

        // TODO validate OTHERS INPUTS
        validateChannel(channel);
        validateInstrument(defaultInstrument);
        validateOctave(defaultOctave);

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

    private static void validateInstrument(int instrument) {
        if (instrument < 0 || instrument > 127) {
            throw new IllegalArgumentException("Instrument must be in range [0, 127]");
        }
    }

    private static void validateOctave(int octave) {
        if (octave < 1 || octave > 10) {
            throw new IllegalArgumentException("Octave must be in range [1, 10]");
        }
    }
}
