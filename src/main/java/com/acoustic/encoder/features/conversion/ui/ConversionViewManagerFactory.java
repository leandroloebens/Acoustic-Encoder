package com.acoustic.encoder.features.conversion.ui;

import com.acoustic.encoder.features.conversion.controller.ConversionController;

public interface ConversionViewManagerFactory {

    ConversionViewManager createViewManager(ConversionController controller);

}
