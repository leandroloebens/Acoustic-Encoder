package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import java.awt.*;

public interface SwingConversionScreenAssembler {

    SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation,
            SwingConversionActionHandler handler
    );

    String getInputText();

    void setInputText(String text);

    int getVolumeSliderValue();

    int getInstrumentSliderValue();

    int getOctaveSliderValue();

    int getBpmSliderValue();

}
