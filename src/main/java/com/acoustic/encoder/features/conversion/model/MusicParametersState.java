package com.acoustic.encoder.features.conversion.model;

import java.util.ArrayList;
import java.util.List;

public class MusicParametersState {

    private final static String INDEX_OUT_OF_BOUNDS = "Index out of bounds";

    private int bpm;
    private List<TrackParameters> tracksParameters;

    public MusicParametersState(int bpm, List<TrackParameters> trackParameters) {
        this.bpm = bpm;
        this.tracksParameters = trackParameters;
    }

    public int getBpm() { return bpm; }

    public void setBpm(int bpm) { this.bpm = bpm; }

    public List<TrackParameters> getAllTracksParameters() { 
        List<TrackParameters> copy = new ArrayList<>();

        for (TrackParameters track : tracksParameters) {
            copy.add(new TrackParameters(
                track.getVolume(),
                track.getOctave(),
                track.getInstrument()
            ));
        }

        return copy; 
    }

    public void setTrackVolume(int index, int volume) {
        if (index >= 0 && index < getNumberOfTracks()) {
            tracksParameters.get(index).setVolume(volume);
        } else {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }
    }

    public void setTrackOctave(int index, int octave) {
        if (index >= 0 && index < getNumberOfTracks()) {
            tracksParameters.get(index).setOctave(octave);
        } else {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }
    }

    public void setTrackInstrument(int index, int instrument) {
        if (index >= 0 && index < getNumberOfTracks()) {
            tracksParameters.get(index).setInstrument(instrument);
        } else {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }
    }

    public TrackParameters getIndexedTrackParameters(int index) {
        if (index < 0 || index >= getNumberOfTracks()) {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }

        TrackParameters original = tracksParameters.get(index);
        return new TrackParameters(
                original.getVolume(),
                original.getOctave(),
                original.getInstrument()
        );
}

    public void setAllTracksParameters(List<TrackParameters> trackParameters) { this.tracksParameters = trackParameters; }

    public void setIndexedTrackParameters(int index, TrackParameters trackParameters) {
        if (index >= 0 && index < getNumberOfTracks())
            tracksParameters.set(index, trackParameters);
        else throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
    }

    public int getNumberOfTracks() { return tracksParameters.size(); }

    @Override
    public String toString() {
        String text = "MusicParametersState{" + "\n\tbpm=" + bpm;

        for (int i = 0; i < getNumberOfTracks(); i++) {
            text = text.concat("\n\tTrack " + i + ": " + tracksParameters.get(i));
        }

        return text;
    }
}
