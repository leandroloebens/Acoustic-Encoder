package com.acoustic.encoder.features.player.audio.midi.command;

import com.acoustic.encoder.domain.music.MusicalCommand;

public interface MidiCommandRegistry {

    MidiCommandHandler getHandler(MusicalCommand command);

}
