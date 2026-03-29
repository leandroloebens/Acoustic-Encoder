package com.acoustic.encoder.audio;

import com.acoustic.encoder.model.MusicModel;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class DefaultSequenceBuilder implements SequenceBuilder {

    public Sequence buildSequence(MusicModel musicModel) {

        try {
            Sequence sequence = new Sequence(Sequence.PPQ, 10);
            Track track = sequence.createTrack();

            // Nota ON
            ShortMessage noteOn = new ShortMessage();
            noteOn.setMessage(ShortMessage.NOTE_ON, 0, 60, 126);

            // Nota OFF
            ShortMessage noteOff = new ShortMessage();
            noteOff.setMessage(ShortMessage.NOTE_OFF, 0, 60, 0);

            track.add(new MidiEvent(noteOn, 1));
            track.add(new MidiEvent(noteOff, 10));
            track.add(new MidiEvent(noteOn, 5));
            track.add(new MidiEvent(noteOff, 14));

            ShortMessage instrument = new ShortMessage();
            instrument.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 0, 0);

            track.add(new MidiEvent(instrument, 0));

            return sequence;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
