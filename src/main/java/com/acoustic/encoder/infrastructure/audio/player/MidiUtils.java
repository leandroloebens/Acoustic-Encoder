package com.acoustic.encoder.infrastructure.audio.player;

import com.acoustic.encoder.domain.music.MusicalNote;
import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Volume;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.Objects;

public class MidiUtils {

    private final static int VOL_CHANGE = 7;

    private final static int SET_TEMPO_TYPE = 0x51;

    private final static int USELESS_VAL = 0;

    public static int noteToMidi(MusicalNote note) {
        Objects.requireNonNull(note, "Note cannot be null!");

        // Music note is represented by MIDI note number
        return 12*(note.octave().value()) + (note.pitch().getValue());

    }

    public static MidiEvent createNoteOnEvent(
            MusicalNote note, int channel, long tick
    ) {
        Objects.requireNonNull(note, "Note cannot be null!");

        return new MidiEvent(createNoteOnMsg(note, channel), tick);
    }

    public static MidiEvent createNoteOffEvent(
            MusicalNote note, int channel, long tick
    ) {
        Objects.requireNonNull(note, "Note cannot be null!");

        return new MidiEvent(createNoteOffMsg(note, channel), tick);
    }

    public static MidiEvent createInstrumentChangeEvent(
            InstrumentId instrument, int channel, long tick
    ) {
        Objects.requireNonNull(instrument, "Instrument cannot be null!");

        return new MidiEvent(createInstrumentChangeMsg(instrument, channel), tick);
    }

    public static MidiEvent createVolumeChangeEvent(
            Volume volume, int channel, long tick
    ) {
        Objects.requireNonNull(volume, "Volume cannot be null!");

        return new MidiEvent(createVolumeChangeMsg(volume, channel), tick);
    }

    public static MidiEvent createTempoChangeEvent(Bpm bpm, long tick) {
        Objects.requireNonNull(bpm, "BPM cannot be null!");

        return new MidiEvent(createTempoChangeMsg(bpm), tick);
    }

    public static ShortMessage createNoteOnMsg(MusicalNote note, int channel) {
        Objects.requireNonNull(note, "Note cannot be null!");

        ShortMessage noteOn = new ShortMessage();

        try {
            noteOn.setMessage(ShortMessage.NOTE_ON, channel, noteToMidi(note), note.velocity().value());
        } catch(InvalidMidiDataException e) {
            throw new RuntimeException("Error in note on message", e);
        }

        return noteOn;
    }

    public static ShortMessage createNoteOffMsg(MusicalNote note, int channel) {
        Objects.requireNonNull(note, "Note cannot be null!");

        ShortMessage noteOff = new ShortMessage();

        try {
            noteOff.setMessage(ShortMessage.NOTE_OFF, channel, noteToMidi(note), USELESS_VAL);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Error in note off message", e);
        }

        return noteOff;
    }

    private static ShortMessage createInstrumentChangeMsg(InstrumentId instrument, int channel) {

        ShortMessage instrumentChange = new ShortMessage();

        try {
            instrumentChange.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument.value(), USELESS_VAL);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Error in instrument control message", e);
        }

        return instrumentChange;
    }

    private static ShortMessage createVolumeChangeMsg(Volume volume, int channel) {

        ShortMessage volumeChange = new ShortMessage();

        try {
            volumeChange.setMessage(ShortMessage.CONTROL_CHANGE, channel, VOL_CHANGE, volume.value());
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Error in volume control message", e);
        }

        return volumeChange;
    }

    private static MetaMessage createTempoChangeMsg(Bpm bpm) {

        int microSecPerBeat = 60000000 / bpm.value();

        MetaMessage tempoChangeMsg = new MetaMessage();

        byte[] data = {
                (byte)(microSecPerBeat >> 16),
                (byte)(microSecPerBeat >> 8),
                (byte) microSecPerBeat
        };

        try {
            tempoChangeMsg.setMessage(SET_TEMPO_TYPE, data, 3);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Error in tempo MetaMessage", e);
        }

        return tempoChangeMsg;
    }

}

