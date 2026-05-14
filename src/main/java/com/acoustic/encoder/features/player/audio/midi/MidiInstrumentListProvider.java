package com.acoustic.encoder.features.player.audio.midi;

import com.acoustic.encoder.features.conversion.view.swing.components.factory.InstrumentListProvider;
import com.acoustic.encoder.shared.dto.InstrumentOption;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import java.util.ArrayList;
import java.util.List;

public class MidiInstrumentListProvider implements InstrumentListProvider {

    private static final String LOAD_INSTRUMENTS_ERROR_MSG = "Error loading MIDI instruments";
    private static final String INVALID_INSTRUMENT_BANK_MSG = "Invalid instrument bank!";

    private final int bank;

    public MidiInstrumentListProvider(int instrumentBank) {
        if (instrumentBank >= 0) this.bank = instrumentBank;
        else throw new IllegalArgumentException(INVALID_INSTRUMENT_BANK_MSG);
    }

    @Override
    public List<InstrumentOption> getInstrumentList() throws Exception {
        List<InstrumentOption> instruments = new ArrayList<>();
        
        Synthesizer synth = MidiSystem.getSynthesizer();
        try {
            synth.open();
            for (Instrument inst : synth.getAvailableInstruments()) {
                String name = inst.getName();
                int program = inst.getPatch().getProgram(); // 0..127
                if (inst.getPatch().getBank() == bank) instruments.add(new InstrumentOption(name, program));
            }
        }
        catch (MidiUnavailableException e) {
            throw new RuntimeException(LOAD_INSTRUMENTS_ERROR_MSG, e);
        }
        finally {
            if (synth.isOpen()) synth.close();
        }

        return instruments;
    }
}
