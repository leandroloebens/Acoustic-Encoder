package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.shared.view.swing.SwingFrame;

public interface SwingConversionScreenAssembler {

    SwingFrame assemble(
            String title,
            int windowWidth,
            int windowHeight,
            Runnable conversionAction,
            Runnable volumeSliderAction,
            Runnable instrumentSliderAction,
            Runnable octaveSliderAction,
            Runnable bpmSliderAction
    );

    String getInputText();

    int getVolumeSliderValue();

    int getInstrumentSliderValue();

    int getOctaveSliderValue();

    int getBpmSliderValue();

}
