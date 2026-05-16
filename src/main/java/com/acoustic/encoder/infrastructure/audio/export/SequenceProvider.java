package com.acoustic.encoder.infrastructure.audio.export;

import javax.sound.midi.Sequence;

public interface SequenceProvider {

    Sequence getSequence();
}
