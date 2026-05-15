package com.acoustic.encoder.domain.event;

import com.acoustic.encoder.domain.music.MusicModel;

public record ConversionCompletedEvent(MusicModel musicModel) {

}
