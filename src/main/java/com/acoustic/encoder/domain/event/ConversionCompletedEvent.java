package com.acoustic.encoder.domain.event;

import com.acoustic.encoder.domain.music.MusicModel;

import java.util.Objects;

public record ConversionCompletedEvent(MusicModel musicModel) {

    public ConversionCompletedEvent {
        Objects.requireNonNull(musicModel, "musicModel must not be null");

    }

}
