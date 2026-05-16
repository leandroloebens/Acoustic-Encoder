package com.acoustic.encoder.features.player.audio.midi.command.handlers;

import com.acoustic.encoder.features.player.audio.midi.command.MidiCommandHandler;
import com.acoustic.encoder.features.player.audio.midi.track.TrackContext;
import com.acoustic.encoder.shared.model.MusicalCommand;
import com.acoustic.encoder.shared.model.MusicalInstruction;

import javax.sound.midi.Track;

public class MidiPlayPreviousHandler implements MidiCommandHandler {

    MidiCommandHandler playNoteHandler;
    MidiCommandHandler silenceHandler;

    public MidiPlayPreviousHandler(MidiCommandHandler playNoteHandler, MidiCommandHandler silenceHandler) {
        this.playNoteHandler = playNoteHandler;
        this.silenceHandler = silenceHandler;
    }

    public TrackContext handle(Track track, TrackContext context, int parameter) {

        if (isPreviousInstructionPlayNote(context.state().previousInstruction())) {

            return playNoteHandler
                    .handle(track, context, context.state().previousInstruction().parameter());
        }

        return silenceHandler.handle(track, context, 0);
    }

    private boolean isPreviousInstructionPlayNote(MusicalInstruction previousInstruction) {

        if (previousInstruction == null) return false;
        return previousInstruction.command() == MusicalCommand.PLAY_NOTE;
    }

}

