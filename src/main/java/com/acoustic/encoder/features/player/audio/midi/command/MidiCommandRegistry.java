package com.acoustic.encoder.features.player.audio.midi.command;

import com.acoustic.encoder.shared.model.MusicalCommand;

public interface MidiCommandRegistry {

    MidiCommandHandler getHandler(MusicalCommand command);

}
