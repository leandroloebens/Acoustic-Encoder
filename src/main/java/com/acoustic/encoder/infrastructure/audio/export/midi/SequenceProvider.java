package com.acoustic.encoder.infrastructure.audio.export.midi;

import javax.sound.midi.Sequence;

public interface SequenceProvider {

    Sequence getSequence();
}
