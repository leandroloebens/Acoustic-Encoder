package com.acoustic.encoder.infrastructure.audio.player;

import javax.sound.midi.*;
import java.util.Objects;

public class DefaultSequencePlayer implements SequencePlayer {

    private final Sequencer sequencer;

    public DefaultSequencePlayer(Sequencer sequencer) throws MidiUnavailableException {

        this.sequencer = sequencer;
        if (!sequencer.isOpen()) this.sequencer.open();
    }

    public void loadSequence(Sequence sequence) throws InvalidMidiDataException {

        Objects.requireNonNull(sequence, "Sequence cannot be null!");

        this.sequencer.setSequence(sequence);
    }

    public void play() {

        this.sequencer.start();
    }

    public void stop() {

        this.sequencer.stop();
    }

    public void rewind() {

        this.sequencer.setTickPosition(0);
    }

    public Sequence getSequence() {
        return this.sequencer.getSequence();
    }

    @Override
    public void closeSequencer() {
        this.sequencer.close();
    }

    @Override
    public long getMicrosecPosition() {
        return this.sequencer.getMicrosecondPosition();
    }

    @Override
    public long getMicrosecDuration() {
        return this.sequencer.getMicrosecondLength();
    }
}
