package com.acoustic.encoder.features.start.view;

import com.acoustic.encoder.features.start.controller.StartController;

public interface StartViewManager {

    void assemble(StartController controller);

    void show();

    void hide();

    void dispose();

}
