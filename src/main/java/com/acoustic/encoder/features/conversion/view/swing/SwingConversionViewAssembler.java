package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import java.awt.*;

public interface SwingConversionViewAssembler {

    SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation
    );

    ConversionViewComponentsWrapper getComponents();

}
