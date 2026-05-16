package com.acoustic.encoder.infrastructure.audio.player.command;

import com.acoustic.encoder.domain.music.MusicalCommand;
import com.acoustic.encoder.infrastructure.audio.player.command.handlers.*;

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
                        MusicalCommand.OFFSET_INSTRUMENT, new MidiOffsetInstrumentHandler(),
                        MusicalCommand.OFFSET_OCTAVE, new MidiOffsetOctaveHandler(),
                        MusicalCommand.PLAY_PREVIOUS, new MidiPlayPreviousHandler(playNoteHandler, silenceHandler),
                        MusicalCommand.NULL_COMMAND, new MidiNullHandler(),
                        MusicalCommand.DELAY_BEATS, new MidiDelayBeatsHandler(),
                        MusicalCommand.OFFSET_LOCAL_BPM, new MidiOffsetLocalBpmHandler()
                )
        );
    }
}
