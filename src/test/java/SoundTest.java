import javax.sound.midi.*;


void main() throws Exception{

    Sequence sequence = new Sequence(Sequence.PPQ, 10);
    Track track = sequence.createTrack();

    // Nota ON
    ShortMessage noteOn = new ShortMessage();
    noteOn.setMessage(ShortMessage.NOTE_ON, 0, 60, 126);

    // Nota OFF
    ShortMessage noteOff = new ShortMessage();
    noteOff.setMessage(ShortMessage.NOTE_OFF, 0, 60, 0);

//        for (int i = 0; i < 10; i++) {
//
//            track.add(new MidiEvent(noteOn, 1 + i*5));
//            track.add(new MidiEvent(noteOff, 10 + i*5));
//        }

    track.add(new MidiEvent(noteOn, 1));
    track.add(new MidiEvent(noteOff, 10));
    track.add(new MidiEvent(noteOn, 5));
    track.add(new MidiEvent(noteOff, 14));

    ShortMessage instrument = new ShortMessage();
    instrument.setMessage(ShortMessage.PROGRAM_CHANGE, 0, 0, 0);

    track.add(new MidiEvent(instrument, 0));

    Sequencer sequencer = MidiSystem.getSequencer();
    sequencer.open();

    sequencer.setSequence(sequence);
    sequencer.setTempoInBPM(120);
    //sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

    sequencer.start();

    while (sequencer.isRunning()) {
        Thread.sleep(1000);
    }

    sequencer.close();

}

