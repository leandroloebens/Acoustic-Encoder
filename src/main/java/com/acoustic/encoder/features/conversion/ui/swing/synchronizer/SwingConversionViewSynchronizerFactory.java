package com.acoustic.encoder.features.conversion.ui.swing.synchronizer;

import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;

public interface SwingConversionViewSynchronizerFactory {

    SwingConversionViewSynchronizer createSynchronizer(
            ConversionViewSwingComponentsWrapper components
    );

}
