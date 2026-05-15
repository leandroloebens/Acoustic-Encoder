package com.acoustic.encoder.infrastructure.audio.midi.track;

import com.acoustic.encoder.domain.voice.Voice;

import javax.sound.midi.Track;

public interface TrackWriter {

    public void writeTrack(
            Track track,
            Voice voice,
            int initialBpm,
            int channel,
            int ppqResolution
    );

    public void writeInitTempoTrack(Track track, int bpm);
}
