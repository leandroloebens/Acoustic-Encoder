package com.acoustic.encoder.features.start.ui.swing.assembler;

import com.acoustic.encoder.features.start.ui.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

import java.awt.*;

public interface SwingStartViewFrameAssembler {

    SwingFrame assembleFrame(String title, Dimension windowInitialSize, int frameExitOperation);

    StartViewSwingComponentsWrapper getComponents();

}
