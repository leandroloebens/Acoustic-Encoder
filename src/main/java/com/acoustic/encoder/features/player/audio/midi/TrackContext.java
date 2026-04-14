package com.acoustic.encoder.features.player.audio.midi;

import com.acoustic.encoder.shared.model.MusicConfig;

record TrackContext(
        int tick,
        int instrument,
        int octave,
        int volume,
        int defaultOctave,
        int defaultInstrument
) {

    static TrackContext initialContext(MusicConfig config) {
        return new TrackContext(
                0,
                config.defaultMidiInstrument(),
                config.defaultOctave(),
                config.defaultVolume(),
                config.defaultOctave(),
                config.defaultMidiInstrument()
        );
    }

    TrackContext withTick(int newTick) {
        return new TrackContext(newTick, instrument, octave, volume, defaultOctave, defaultInstrument);
    }

    TrackContext withVolume(int newVolume) {
        return new TrackContext(tick, instrument, octave, newVolume, defaultOctave, defaultInstrument);
    }

    TrackContext doubleVolume() {
        int newVolume = 2*volume;
        return withVolume(Math.min(newVolume, MidiUtils.VOL_MAX));
    }

    TrackContext withInstrument(int newInstrument) {
        return new TrackContext(tick, newInstrument, octave, volume, defaultOctave, defaultInstrument);
    }

    TrackContext incrementInstrument(int val) {
        int newInstrument = instrument + val;
        return withInstrument(newInstrument > MidiUtils.INSTRUMENT_MAX ? defaultInstrument : newInstrument);
    }

    TrackContext withOctave(int newOctave) {
        return new TrackContext(tick, instrument, newOctave, volume, defaultOctave, defaultInstrument);
    }

    TrackContext incrementOctave(int val) {
        int newOctave = octave + val;
        return withOctave(newOctave > MidiUtils.OCTAVE_MAX ? defaultOctave : newOctave);
    }


}
