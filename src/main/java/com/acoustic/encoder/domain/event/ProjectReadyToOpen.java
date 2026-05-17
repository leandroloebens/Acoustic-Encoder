package com.acoustic.encoder.domain.event;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

public record ProjectReadyToOpen(UserConversionInput project) {
}
