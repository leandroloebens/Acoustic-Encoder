package com.acoustic.encoder.features.start.event;

import com.acoustic.encoder.features.conversion.dto.MusicProject;

import java.util.Objects;

public record ProjectReadyToOpenEvent(MusicProject project) {

    public ProjectReadyToOpenEvent {
        Objects.requireNonNull(project, "project must not be null");

    }
}
