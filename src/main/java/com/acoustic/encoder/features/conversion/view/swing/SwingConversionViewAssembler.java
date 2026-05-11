package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import java.awt.*;

public interface SwingConversionViewAssembler {

    SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation,
            MusicParameters initialParameters,
            SwingConversionViewActionHandler handler
    );

    String getInputText();

    void setInputText(String text);

    int getVolumeValue();

    int getInstrumentValue();

    int getOctaveValue();

    int getBpmValue();

}
