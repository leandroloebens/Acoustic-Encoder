package com.acoustic.encoder.infrastructure.audio.player.command;

import com.acoustic.encoder.domain.music.MusicalCommand;

import java.util.Map;
import java.util.Objects;

public class DefaultMidiCommandRegistry implements MidiCommandRegistry {

    private final Map<MusicalCommand, MidiCommandHandler> handlers;

    public DefaultMidiCommandRegistry(Map<MusicalCommand, MidiCommandHandler> handlers) {

        this.handlers = Map.copyOf(Objects.requireNonNull(handlers, "Handlers map cannot be null"));
    }

    public MidiCommandHandler getHandler(MusicalCommand command) {

        MidiCommandHandler handler = handlers.get(command);

        if (handler == null) {
            throw new IllegalArgumentException("No MIDI handler registered for command: " + command);
        }

        return handler;
    }
}
