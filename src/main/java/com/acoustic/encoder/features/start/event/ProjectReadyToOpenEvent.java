package com.acoustic.encoder.features.start.event;

import com.acoustic.encoder.features.conversion.dto.MusicProject;

import java.util.Objects;

public record ProjectReadyToOpenEvent(MusicProject project) {

    public ProjectReadyToOpenEvent {
        Objects.requireNonNull(project, "Project must not be null");

    }
}
