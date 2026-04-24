package com.acoustic.encoder.features.player.audio.midi;

import com.acoustic.encoder.shared.model.*;

import javax.sound.midi.*;
import java.util.Objects;

public class DefaultSequenceBuilder implements SequenceBuilder {

    private final static float DIVISION_TYPE = Sequence.PPQ;
    private final static int PPQ_RESOLUTION = 480;
    private final static int DEFAULT_CHANNEL = 0;

    TrackWriter trackWriter;

    public DefaultSequenceBuilder(TrackWriter trackWriter) {

        this.trackWriter = Objects.requireNonNull(trackWriter, "Track writer must not be null!");
    }

    public Sequence buildSequence(MusicModel musicModel) throws InvalidMidiDataException {

        Sequence sequence = new Sequence(DIVISION_TYPE, PPQ_RESOLUTION);

        Track tempoTrack = sequence.createTrack();
        trackWriter.writeInitTempoTrack(tempoTrack, musicModel.config().bpm());

        Track mainTrack = sequence.createTrack();
        trackWriter.writeTrack(
                mainTrack,
                musicModel.musicalInstructions(),
                musicModel.config(),
                DEFAULT_CHANNEL,
                PPQ_RESOLUTION
        );

        return sequence;
    }

}