package com.acoustic.encoder.audio.midi;

import javax.sound.midi.*;
import java.util.Objects;

public class DefaultSequencePlayer implements SequencePlayer {

    private final Sequencer sequencer;
    private int currentBpm;

    public DefaultSequencePlayer(Sequencer sequencer) throws MidiUnavailableException {

        this.sequencer = sequencer;
        if (!sequencer.isOpen()) this.sequencer.open();
    }

    public void loadSequence(Sequence sequence) throws InvalidMidiDataException {

        Objects.requireNonNull(sequence, "Sequence cannot be null!");

        this.sequencer.setSequence(sequence);
    }

    public void setBpm(int bpm) {

        this.currentBpm = bpm;
    }

    public void play() {

        this.sequencer.start();
        this.sequencer.setTempoInBPM(this.currentBpm);
    }

    public void stop() {

        this.sequencer.stop();
    }

    public void rewind() {

        this.sequencer.setTickPosition(0);
    }
}
