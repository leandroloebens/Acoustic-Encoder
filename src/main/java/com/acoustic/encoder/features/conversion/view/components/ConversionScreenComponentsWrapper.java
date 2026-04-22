package com.acoustic.encoder.features.conversion.view.components;

import com.acoustic.encoder.shared.view.Button;

public record ComponentsWrapper(
        Button conversionButton
) {}

// Create a conversion component factory and pass it through main to the screen factory
// Create a component factory (interface) atribute at the screen factory