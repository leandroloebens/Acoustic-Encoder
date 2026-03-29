package com.acoustic.encoder.service;

import com.acoustic.encoder.model.MusicConfig;
import com.acoustic.encoder.model.MusicModel;

public interface ConversionService {

    MusicModel textToMusic(String text, MusicConfig config);

}
