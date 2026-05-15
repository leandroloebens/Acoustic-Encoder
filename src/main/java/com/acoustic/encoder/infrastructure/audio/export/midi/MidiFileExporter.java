package com.acoustic.encoder.infrastructure.audio.export.midi;

import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.ports.MusicExporter;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MidiFileExporter implements MusicExporter {

    private final static int MIDI_FORMAT = 1;

    private final SequenceProvider sequenceProvider;

    public MidiFileExporter(SequenceProvider sequenceProvider) {
        this.sequenceProvider = Objects.requireNonNull(
                sequenceProvider,
                "SequenceProvider cannot be null"
        );
    }

    @Override
    public void export(File file) throws MusicExportException {

        Objects.requireNonNull(file, "Export file cannot be null!");
        Sequence sequence = Objects.requireNonNull(
                sequenceProvider.getSequence(), "Sequence cannot be null!"
        );

        try {

            MidiSystem.write(sequence, MIDI_FORMAT, file);

        } catch (IOException e) {

            throw new MusicExportException("Failed to write Midi file to : " + file.getAbsolutePath(), e);
        }

    }

}
