package com.acoustic.encoder.features.conversion.view;

import com.acoustic.encoder.features.conversion.controller.ConversionController;

public interface ConversionViewManager {

    void startFrame(ConversionController controller);

    void showFrame();

    void hideFrame();

    void disposeFrame();

}
