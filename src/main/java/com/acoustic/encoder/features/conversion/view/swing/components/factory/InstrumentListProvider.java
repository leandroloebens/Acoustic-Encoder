package com.acoustic.encoder.features.conversion.view.swing.components.factory;

import com.acoustic.encoder.domain.music.InstrumentOption;

import java.util.List;

public interface InstrumentListProvider {

    List<InstrumentOption> getInstrumentList() throws Exception;

}
