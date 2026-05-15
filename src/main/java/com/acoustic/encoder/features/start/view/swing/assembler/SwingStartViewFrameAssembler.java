package com.acoustic.encoder.features.start.view.swing.assembler;

import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import java.awt.*;

public interface SwingStartViewFrameAssembler {

    SwingFrame assembleFrame(String title, Dimension windowInitialSize, int frameExitOperation);

    StartViewSwingComponentsWrapper getComponents();

}
