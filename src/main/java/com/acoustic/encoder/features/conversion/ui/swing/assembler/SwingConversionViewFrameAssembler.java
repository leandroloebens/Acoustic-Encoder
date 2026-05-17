package com.acoustic.encoder.features.conversion.ui.swing.assembler;

import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

import java.awt.*;

public interface SwingConversionViewFrameAssembler {

    SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation
    );

    ConversionViewSwingComponentsWrapper getComponents();

}
