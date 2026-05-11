package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.shared.model.VoiceConfig;

record TrackContext(
        TrackSettings settings,
        TrackState state
) {

    static TrackContext initialContext(VoiceConfig config, int noteTickDuration, int channel) {
        return new TrackContext(
                new TrackSettings(channel, config.defaultMidiInstrument(), config.defaultOctave()),
                new TrackState(
                        config.defaultBpm(),
                        noteTickDuration,
                        0,
                        config.defaultMidiInstrument(),
                        config.defaultOctave(),
                        config.defaultVolume()
                )
        );
    }

    TrackContext withTick(int newTick) {
        return new TrackContext(settings, state.withTick(newTick));
    }

    TrackContext withVolume(int newVolume) {
        return new TrackContext(settings, state.withVolume(newVolume));
    }

    TrackContext doubleVolume() {
        int newVolume = 2*state.volume();
        return withVolume(Math.min(newVolume, MidiUtils.VOL_MAX));
    }

    TrackContext withInstrument(int newInstrument) {
        return new TrackContext(settings, state.withInstrument(newInstrument));
    }

    TrackContext incrementInstrument(int val) {
        int newInstrument = state.instrument() + val;
        return withInstrument(newInstrument > MidiUtils.INSTRUMENT_MAX ? settings.defaultInstrument() : newInstrument);
    }

    TrackContext withOctave(int newOctave) {
        return new TrackContext(settings, state.withOctave(newOctave));
    }

    TrackContext incrementOctave(int val) {
        int newOctave = state.octave() + val;
        return withOctave(newOctave > MidiUtils.OCTAVE_MAX ? settings.defaultOctave() : newOctave);
    }

}
