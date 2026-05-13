package com.acoustic.encoder.features.player.audio.midi.command;

import com.acoustic.encoder.features.player.audio.midi.command.handlers.*;
import com.acoustic.encoder.shared.model.MusicalCommand;

import java.util.Map;

public class DefaultMidiCommandRegistryFactory {

    public DefaultMidiCommandRegistry create() {
        MidiCommandHandler playNoteHandler = new MidiPlayNoteHandler();
        MidiCommandHandler silenceHandler = new MidiSilenceHandler();

        return new DefaultMidiCommandRegistry(
                Map.of(
                        MusicalCommand.PLAY_NOTE, playNoteHandler,
                        MusicalCommand.SILENCE, silenceHandler,
                        MusicalCommand.MULTIPLY_VOLUME, new MidiMultiplyVolumeHandler(),
                        MusicalCommand.CHANGE_INSTRUMENT, new MidiChangeInstrumentHandler(),
                        MusicalCommand.INCREMENT_INSTRUMENT, new MidiIncrementInstrumentHandler(),
                        MusicalCommand.INCREMENT_OCTAVE, new MidiIncrementOctaveHandler(),
                        MusicalCommand.PLAY_PREVIOUS, new MidiPlayPreviousHandler(playNoteHandler, silenceHandler),
                        MusicalCommand.NULL_COMMAND, new MidiNullHandler(),
                        MusicalCommand.DELAY_BEATS, new MidiDelayBeatsHandler()
                )
        );
    }
}
