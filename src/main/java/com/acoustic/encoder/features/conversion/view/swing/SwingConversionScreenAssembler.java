package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.shared.view.swing.SwingFrame;

public interface SwingConversionScreenAssembler {

    SwingFrame assemble(
            String title,
            int windowWidth,
            int windowHeight,
            SwingEventHandler handler
    );

    String getInputText();

    int getVolumeSliderValue();

    int getInstrumentSliderValue();

    int getOctaveSliderValue();

    int getBpmSliderValue();

}
