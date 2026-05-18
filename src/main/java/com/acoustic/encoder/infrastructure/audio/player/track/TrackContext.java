package com.acoustic.encoder.infrastructure.audio.player.track;

import com.acoustic.encoder.domain.music.MusicalInstruction;
import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.domain.voice.VoiceConfig;

import java.util.Objects;

public record TrackContext(
        TrackSettings settings,
        TrackState state
) {

    public static TrackContext initialContext(
            VoiceConfig config, int noteTickDuration, int channel, int ppqResolution, int noteVelocity, Bpm initialBpm
    ) {
        Objects.requireNonNull(config, "VoiceConfig cannot be null");
        Objects.requireNonNull(initialBpm, "Initial BPM cannot be null");

        return new TrackContext(
                new TrackSettings(
                        channel,
                        ppqResolution,
                        config.defaultInstrument(),
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
                        config.defaultInstrument(),
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

    public TrackContext withVolume(Volume newVolume) {
        return new TrackContext(settings, state.withVolume(newVolume));
    }

    public TrackContext withInstrument(InstrumentId newInstrument) {
        return new TrackContext(settings, state.withInstrument(newInstrument));
    }

    public TrackContext withOctave(Octave newOctave) {
        return new TrackContext(settings, state.withOctave(newOctave));
    }

    public TrackContext withPreviousInstruction(MusicalInstruction newPreviousInstruction) {
        return new TrackContext(settings, state.withPreviousInstruction(newPreviousInstruction));
    }

    public TrackContext withLocalBpm(Bpm newLocalBpm) {
        return new TrackContext(settings, state.withLocalBpm(newLocalBpm));
    }

}
