package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;

public interface SwingEventHandler {

    void onConvert();

    void onVolumeChange();

    void onOctaveChange();

    void onBpmChange();

    void onInstrumentChange();

}
