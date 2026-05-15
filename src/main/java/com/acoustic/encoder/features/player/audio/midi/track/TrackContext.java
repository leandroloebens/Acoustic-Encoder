package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.domain.music.MusicalInstruction;
import com.acoustic.encoder.domain.voice.VoiceConfig;

public record TrackContext(
        TrackSettings settings,
        TrackState state
) {

    public static TrackContext initialContext(VoiceConfig config, int noteTickDuration, int channel, int ppqResolution, int noteVelocity, int initialBpm) {
        return new TrackContext(
                new TrackSettings(
                        channel,
                        ppqResolution,
                        config.defaultMidiInstrument(),
                        config.defaultOctave(),
                        noteVelocity,
                        initialBpm,
                        noteTickDuration
                ),
                new TrackState(
                        null,
                        initialBpm,
                        noteTickDuration,
                        0,
                        config.defaultMidiInstrument(),
                        config.defaultOctave(),
                        config.defaultVolume()
                )
        );
    }

    public TrackContext withNoteTickDuration(int newNoteTickDuration) {
        return new TrackContext(settings, state.withNoteTickDuration(newNoteTickDuration));
    }

    public TrackContext withTick(long newTick) {
        return new TrackContext(settings, state.withTick(newTick));
    }

    public TrackContext withVolume(int newVolume) {
        return new TrackContext(settings, state.withVolume(newVolume));
    }

    public TrackContext withInstrument(int newInstrument) {
        return new TrackContext(settings, state.withInstrument(newInstrument));
    }

    public TrackContext withOctave(int newOctave) {
        return new TrackContext(settings, state.withOctave(newOctave));
    }

    public TrackContext withPreviousInstruction(MusicalInstruction newPreviousInstruction) {
        return new TrackContext(settings, state.withPreviousInstruction(newPreviousInstruction));
    }

    public TrackContext withLocalBpm(int newLocalBpm) {
        return new TrackContext(settings, state.withLocalBpm(newLocalBpm));
    }

}
