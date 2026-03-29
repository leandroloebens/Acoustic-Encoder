package com.acoustic.encoder.audio;

import javax.sound.midi.*;

public class DefaultSequencePlayer implements SequencePlayer {

    private final Sequencer sequencer;

    public DefaultSequencePlayer(Sequencer sequencer) throws MidiUnavailableException, InvalidMidiDataException {

        this.sequencer = sequencer;
        if (!sequencer.isOpen()) this.sequencer.open();
    }

    public void loadSequence(Sequence sequence) throws InvalidMidiDataException {

        this.sequencer.setSequence(sequence);
    }

    public void setBpm(int bpm) {

        this.sequencer.setTempoInBPM(bpm);
    }

    public void play() {

        this.sequencer.start();
    }

    public void stop() {

        this.sequencer.stop();
    }

    public void rewind() {}
}
