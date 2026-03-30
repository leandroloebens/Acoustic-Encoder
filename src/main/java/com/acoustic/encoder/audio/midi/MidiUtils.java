package com.acoustic.encoder.audio.midi;

import com.acoustic.encoder.model.MusicalNote;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import java.util.Objects;

public class MidiUtils {

    public final static int VOL_MIN = 0;
    public final static int VOL_MAX = 127;
    private final static int VOL_CHANGE = 7;

    public final static int INSTRUMENT_MAX = 127;

    public final static int OCTAVE_MAX = 9;

    private final static int USELESS_VAL = 0;

    public static int noteToMidi(MusicalNote note) {

        // Music note is represented by MIDI note number
        return 12*(note.octave()-1) + (note.pitch().getValue());

    }

    public static ShortMessage createNoteOn(MusicalNote note, int channel) throws InvalidMidiDataException {

        Objects.requireNonNull(note, "Note cannot be null!");

        ShortMessage noteOn = new ShortMessage();
        noteOn.setMessage(ShortMessage.NOTE_ON, channel, noteToMidi(note), note.velocity());

        return noteOn;
    }

    public static ShortMessage createNoteOff(MusicalNote note, int channel) throws InvalidMidiDataException {

        ShortMessage noteOff = new ShortMessage();
        noteOff.setMessage(ShortMessage.NOTE_OFF, channel, noteToMidi(note), USELESS_VAL);

        return noteOff;
    }

    public static ShortMessage createInstrumentChange(int instrumentVal, int channel) throws InvalidMidiDataException {

        ShortMessage instrumentChange = new ShortMessage();
        instrumentChange.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrumentVal, USELESS_VAL);

        return instrumentChange;
    }

    public static ShortMessage createVolumeChange(int volume, int channel) throws InvalidMidiDataException {

        if (volume < VOL_MIN || volume > VOL_MAX)
            throw new IllegalArgumentException("Volume must be between 0 and 127!");

        ShortMessage volumeChange = new ShortMessage();
        volumeChange.setMessage(ShortMessage.CONTROL_CHANGE, channel, VOL_CHANGE, volume);

        return volumeChange;
    }



}

