package com.acoustic.encoder.infrastructure.audio.player.command;

import com.acoustic.encoder.domain.music.MusicalCommand;

public interface MidiCommandRegistry {

    MidiCommandHandler getHandler(MusicalCommand command);

}
