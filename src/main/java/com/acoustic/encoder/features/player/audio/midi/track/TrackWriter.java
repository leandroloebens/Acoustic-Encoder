package com.acoustic.encoder.features.player.audio.midi.track;

import com.acoustic.encoder.shared.model.Voice;

import javax.sound.midi.Track;

public interface TrackWriter {

    public void writeTrack(
            Track track,
            Voice voice,
            int channel,
            int ppqResolution
    );

    public void writeInitTempoTrack(Track track, int bpm);
}
