package com.acoustic.encoder.features.player.export.midi;

import javax.sound.midi.Sequence;

public interface SequenceProvider {

    Sequence getSequence();
}
