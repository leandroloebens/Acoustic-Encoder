package com.acoustic.encoder.features.conversion.view.swing.frame.assembler;

import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import java.awt.*;

public interface SwingConversionViewFrameAssembler {

    SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation
    );

    ConversionViewSwingComponentsWrapper getComponents();

}
