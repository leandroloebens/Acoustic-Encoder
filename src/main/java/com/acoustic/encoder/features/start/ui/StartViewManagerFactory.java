package com.acoustic.encoder.features.start.ui;

import com.acoustic.encoder.features.start.controller.StartController;

public interface StartViewManagerFactory {

    StartViewManager createViewManager(StartController controller);

}
