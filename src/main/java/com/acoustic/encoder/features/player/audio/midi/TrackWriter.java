package com.acoustic.encoder.features.player.audio.midi;

import com.acoustic.encoder.shared.model.MusicConfig;
import com.acoustic.encoder.shared.model.MusicalInstruction;

import javax.sound.midi.Track;
import java.util.List;

public interface TrackWriter {

    public void writeTrack(
            Track track,
            List<MusicalInstruction> musicalInstructions,
            MusicConfig config,
            int channel,
            int ppqResolution
    );

    public void writeInitTempoTrack(Track track, int bpm);
}
