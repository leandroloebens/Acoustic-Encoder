package com.acoustic.encoder.features.conversion.view;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.shared.model.MusicConfig;

public interface ConversionScreenManager {

    void startFrame(ConversionController controller);

    void showFrame();

    void hideFrame();

    void disposeFrame();

    void setInitialDefaultParameters(MusicConfig parameters);

}
