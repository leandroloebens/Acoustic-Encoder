package com.acoustic.encoder.features.conversion.ui;

import com.acoustic.encoder.features.conversion.controller.ConversionController;

public interface ConversionViewManager {

    void assemble(ConversionController controller);

    void show();

    void hide();

    void dispose();

}
