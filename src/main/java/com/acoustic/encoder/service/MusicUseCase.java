package com.acoustic.encoder.service;

public interface MusicUseCase {

    public void textToMusic(String text, int instrument, int bpm, int defaultOctave, int volume);

}
