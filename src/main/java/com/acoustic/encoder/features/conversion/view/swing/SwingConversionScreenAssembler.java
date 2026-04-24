package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.shared.view.swing.SwingFrame;

public interface SwingConversionScreenAssembler {

    SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingEventHandler handler
    );

    String getInputText();

    void setInputText(String text);

    int getVolumeSliderValue();

    int getInstrumentSliderValue();

    int getOctaveSliderValue();

    int getBpmSliderValue();

}
