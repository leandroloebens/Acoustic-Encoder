package com.acoustic.encoder.features.conversion.model;

import java.util.ArrayList;
import java.util.List;

public class MusicParametersState {

    private final static String INDEX_OUT_OF_BOUNDS = "Index out of bounds";

    private int bpm;
    private List<VoiceParameters> tracksParameters;

    public MusicParametersState(int bpm, List<VoiceParameters> voiceParameters) {
        this.bpm = bpm;
        this.tracksParameters = voiceParameters;
    }

    public int getBpm() { return bpm; }

    public void setBpm(int bpm) { this.bpm = bpm; }

    public List<VoiceParameters> getAllTracksParameters() {
        List<VoiceParameters> copy = new ArrayList<>();

        for (VoiceParameters track : tracksParameters) {
            copy.add(new VoiceParameters(
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

    public VoiceParameters getIndexedTrackParameters(int index) {
        if (index < 0 || index >= getNumberOfTracks()) {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }

        VoiceParameters original = tracksParameters.get(index);
        return new VoiceParameters(
                original.getVolume(),
                original.getOctave(),
                original.getInstrument()
        );
    }

    public void setAllTracksParameters(List<VoiceParameters> voiceParameters) { this.tracksParameters = voiceParameters; }

    public void setIndexedTrackParameters(int index, VoiceParameters voiceParameters) {
        if (index >= 0 && index < getNumberOfTracks())
            tracksParameters.set(index, voiceParameters);
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
