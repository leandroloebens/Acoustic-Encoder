import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class InstrumentTest {

    void main() throws Exception {

        Synthesizer synth = MidiSystem.getSynthesizer();
        synth.open();

        Instrument[] instruments = synth.getAvailableInstruments();

        int count = 0;
        for (Instrument i : instruments) {
            System.out.println(count + " " + i.getName());
            count++;
        }
    }
}
