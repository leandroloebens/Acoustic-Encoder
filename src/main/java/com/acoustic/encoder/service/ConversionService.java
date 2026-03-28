package com.acoustic.encoder.service;

import com.acoustic.encoder.model.MusicModel;

public interface ConversionService {

    public MusicModel textToMusic(String text, int instrument, int bpm, int defaultOctave, int volume);

}
