package com.acoustic.encoder.features.conversion.view;

import com.acoustic.encoder.shared.model.MusicConfig;

public interface ConversionScreenComponentsAssembler {

    void assemble(int windowWidth, int windowHeight);

    void showFrame();

    void hideFrame();

    // void destroyFrame();

    MusicConfig getDefaultParameters();

    String getInputText();

    void setDefaultParameters(MusicConfig parameters);

    void setConversionAction(Runnable action);

}
