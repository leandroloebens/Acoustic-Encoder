package com.acoustic.encoder.infrastructure.audio.player.track;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.voice.Voice;

import javax.sound.midi.Track;

public interface TrackWriter {

    void writeTrack(
            Track track,
            Voice voice,
            Bpm initialBpm,
            int channel,
            int ppqResolution
    );

    void writeInitTempoTrack(Track track, Bpm bpm);
}
