package com.acoustic.encoder.features.conversion.ui.swing.frame.binder;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

public interface SwingConversionViewFrameBinder {

    void bind(ConversionController controller, SwingFrame frame, ConversionViewSwingComponentsWrapper components);

    void unbind();

}
