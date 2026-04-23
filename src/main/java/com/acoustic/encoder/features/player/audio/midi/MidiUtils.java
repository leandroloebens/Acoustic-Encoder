package com.acoustic.encoder.features.player.audio.midi;

import com.acoustic.encoder.features.player.model.MusicalNote;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.Objects;

public class MidiUtils {

    public final static int VOL_MIN = 0;
    public final static int VOL_MAX = 127;
    private final static int VOL_CHANGE = 7;

    public final static int INSTRUMENT_MAX = 127;

    public final static int OCTAVE_MAX = 9;

    private final static int SET_TEMPO_TYPE = 0x51;

    private final static int USELESS_VAL = 0;

    public static int noteToMidi(MusicalNote note) {

        // Music note is represented by MIDI note number
        return 12*(note.octave()-1) + (note.pitch().getValue());

    }

    public static MidiEvent createNoteOnEvent(
            MusicalNote note, int channel, int tick
    ) throws InvalidMidiDataException {

        return new MidiEvent(createNoteOnMsg(note, channel), tick);
    }

    public static MidiEvent createNoteOffEvent(
            MusicalNote note, int channel, int tick
    ) throws InvalidMidiDataException {

        return new MidiEvent(createNoteOffMsg(note, channel), tick);
    }

    public static MidiEvent createInstrumentChangeEvent(
            int instrumentVal, int channel, int tick
    ) throws InvalidMidiDataException {

        return new MidiEvent(createInstrumentChangeMsg(instrumentVal, channel), tick);
    }

    public static MidiEvent createVolumeChangeEvent(
            int volume, int channel, int tick
    ) throws InvalidMidiDataException {

        return new MidiEvent(createVolumeChangeMsg(volume, channel), tick);
    }

    public static MidiEvent createTempoChangeEvent(int bpm, int tick) throws InvalidMidiDataException {

        return new MidiEvent(createTempoChangeMsg(bpm), tick);
    }

    public static ShortMessage createNoteOnMsg(MusicalNote note, int channel) throws InvalidMidiDataException {

        Objects.requireNonNull(note, "Note cannot be null!");

        ShortMessage noteOn = new ShortMessage();
        noteOn.setMessage(ShortMessage.NOTE_ON, channel, noteToMidi(note), note.velocity());

        return noteOn;
    }

    public static ShortMessage createNoteOffMsg(MusicalNote note, int channel) throws InvalidMidiDataException {

        ShortMessage noteOff = new ShortMessage();
        noteOff.setMessage(ShortMessage.NOTE_OFF, channel, noteToMidi(note), USELESS_VAL);

        return noteOff;
    }

    private static ShortMessage createInstrumentChangeMsg(int instrumentVal, int channel) throws InvalidMidiDataException {

        ShortMessage instrumentChange = new ShortMessage();
        instrumentChange.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrumentVal, USELESS_VAL);

        return instrumentChange;
    }

    private static ShortMessage createVolumeChangeMsg(int volume, int channel) throws InvalidMidiDataException {

        if (volume < VOL_MIN || volume > VOL_MAX)
            throw new IllegalArgumentException("Volume must be between 0 and 127!");

        ShortMessage volumeChange = new ShortMessage();
        volumeChange.setMessage(ShortMessage.CONTROL_CHANGE, channel, VOL_CHANGE, volume);

        return volumeChange;
    }

    private static MetaMessage createTempoChangeMsg(int bpm) throws InvalidMidiDataException {

        int microSecPerBeat = 60000000 / bpm;

        MetaMessage tempoChangeMsg = new MetaMessage();

        byte[] data = {
                (byte)(microSecPerBeat >> 16),
                (byte)(microSecPerBeat >> 8),
                (byte) microSecPerBeat
        };

        tempoChangeMsg.setMessage(SET_TEMPO_TYPE, data, 3);

        return tempoChangeMsg;
    }

}

