package com.acoustic.encoder.domain.event;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

import java.util.Objects;

public record ProjectReadyToOpen(UserConversionInput project) {

    public ProjectReadyToOpen {
        Objects.requireNonNull(project, "project must not be null");

    }
}
