package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.shared.view.swing.SwingFrame;

public interface SwingConversionScreenAssembler {

    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingConversionEventHandler handler
    );

    String getInputText();

    int getVolumeSliderValue();

    int getInstrumentSliderValue();

    int getOctaveSliderValue();

    int getBpmSliderValue();

}
