package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.features.player.audio.midi.MidiUtils;
import com.acoustic.encoder.shared.model.MusicalInstruction;
import com.acoustic.encoder.shared.model.VoiceConfig;

public record TrackContext(
        TrackSettings settings,
        TrackState state
) {

    public static TrackContext initialContext(VoiceConfig config, int noteTickDuration, int channel, int ppqResolution, int noteVelocity) {
        return new TrackContext(
                new TrackSettings(channel, ppqResolution, config.defaultMidiInstrument(), config.defaultOctave(), noteVelocity),
                new TrackState(
                        null,
                        config.defaultBpm(),
                        noteTickDuration,
                        0,
                        config.defaultMidiInstrument(),
                        config.defaultOctave(),
                        config.defaultVolume()
                )
        );
    }

    public TrackContext withTick(int newTick) {
        return new TrackContext(settings, state.withTick(newTick));
    }

    public TrackContext withVolume(int newVolume) {
        return new TrackContext(settings, state.withVolume(newVolume));
    }

    public TrackContext multiplyVolumeBy(int factor) {
        int newVolume = factor*state.volume();
        return withVolume(Math.min(newVolume, MidiUtils.VOL_MAX));
    }

    public TrackContext withInstrument(int newInstrument) {
        return new TrackContext(settings, state.withInstrument(newInstrument));
    }

    public TrackContext incrementInstrument(int val) {
        int newInstrument = state.instrument() + val;
        return withInstrument(newInstrument > MidiUtils.INSTRUMENT_MAX ? settings.defaultInstrument() : newInstrument);
    }

    public TrackContext withOctave(int newOctave) {
        return new TrackContext(settings, state.withOctave(newOctave));
    }

    public TrackContext incrementOctave(int val) {
        int newOctave = state.octave() + val;
        return withOctave(newOctave > MidiUtils.OCTAVE_MAX ? settings.defaultOctave() : newOctave);
    }

    public TrackContext withPreviousInstruction(MusicalInstruction newPreviousInstruction) {
        return new TrackContext(settings, state.withPreviousInstruction(newPreviousInstruction));
    }

    public TrackContext withDelay(int tickDelay) {
        return withTick(state.tick() + tickDelay);
    }

}
