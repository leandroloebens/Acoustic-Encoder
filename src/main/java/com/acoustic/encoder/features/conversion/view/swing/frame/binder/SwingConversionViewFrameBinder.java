package com.acoustic.encoder.features.conversion.view.swing.frame.binder;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

public interface SwingConversionViewFrameBinder {

    void bind(ConversionController controller, SwingFrame frame, ConversionViewSwingComponentsWrapper components);

    void unbind();

}
