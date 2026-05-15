package com.acoustic.encoder.infrastructure.audio.midi.command;

import com.acoustic.encoder.domain.music.MusicalCommand;

public interface MidiCommandRegistry {

    MidiCommandHandler getHandler(MusicalCommand command);

}
