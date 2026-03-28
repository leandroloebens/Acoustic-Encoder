package com.acoustic.encoder.audio.midi;

import com.acoustic.encoder.model.MusicalNote;

public class MidiNoteMapper {

    public static int mapNote(MusicalNote note) {

        // Music note is represented by MIDI note number
        return 12*(note.octave()-1) + (note.pitch().getValue());

    }
}
