package com.acoustic.encoder.features.start.controller;

import com.acoustic.encoder.features.conversion.ports.TextRepository;

public class DefaultStartController implements StartController {

    private final TextRepository textRepository;

    public DefaultStartController(TextRepository textRepository) {
        if (textRepository == null) throw new IllegalArgumentException();
        this.textRepository = textRepository;
    }

}
