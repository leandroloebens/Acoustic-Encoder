package com.acoustic.encoder.features.conversion.dto;

import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;

import java.util.ArrayList;
import java.util.List;

public class MusicParametersState {

    private final static String INDEX_OUT_OF_BOUNDS = "Index out of bounds";

    private Bpm bpm;
    private List<VoiceParametersState> voices;

    public MusicParametersState() {}

    public MusicParametersState(Bpm bpm, List<VoiceParametersState> voiceParameters) {
        this.bpm = bpm;
        this.voices = voiceParameters;
    }

    public Bpm getBpm() { return bpm; }

    public void setBpm(Bpm bpm) { this.bpm = bpm; }

    public List<VoiceParametersState> getAllVoices() {
        List<VoiceParametersState> copy = new ArrayList<>();

        for (VoiceParametersState track : voices) {
            copy.add(new VoiceParametersState(
                track.getVolume(),
                track.getOctave(),
                track.getInstrument()
            ));
        }

        return copy; 
    }

    public void setVoiceVolume(int index, Volume volume) {
        if (index >= 0 && index < getNumberOfVoices()) {
            voices.get(index).setVolume(volume);
        } else {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }
    }

    public void setVoiceOctave(int index, Octave octave) {
        if (index >= 0 && index < getNumberOfVoices()) {
            voices.get(index).setOctave(octave);
        } else {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }
    }

    public void setVoiceInstrument(int index, InstrumentId instrument) {
        if (index >= 0 && index < getNumberOfVoices()) {
            voices.get(index).setInstrument(instrument);
        } else {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }
    }

    public VoiceParametersState getIndexedVoice(int index) {
        if (index < 0 || index >= getNumberOfVoices()) {
            throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
        }

        VoiceParametersState original = voices.get(index);
        return new VoiceParametersState(
                original.getVolume(),
                original.getOctave(),
                original.getInstrument()
        );
    }

    public void setAllVoices(List<VoiceParametersState> voiceParameters) { this.voices = voiceParameters; }

    public void setIndexedVoice(int index, VoiceParametersState voiceParameters) {
        if (index >= 0 && index < getNumberOfVoices())
            voices.set(index, voiceParameters);
        else throw new IllegalArgumentException(INDEX_OUT_OF_BOUNDS);
    }

    public int getNumberOfVoices() { return voices.size(); }

    @Override
    public String toString() {
        String text = "MusicParametersState{" + "\n\tbpm=" + bpm;

        for (int i = 0; i < getNumberOfVoices(); i++) {
            text = text.concat("\n\tVoice " + i + ": " + voices.get(i));
        }

        return text;
    }
}
