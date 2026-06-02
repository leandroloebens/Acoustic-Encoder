package com.acoustic.encoder.infrastructure.ui_shared.swing;

import com.acoustic.encoder.infrastructure.ui_shared.UiThreadDispatcher;

import javax.swing.*;

public class SwingThreadDispatcher implements UiThreadDispatcher {

    @Override
    public void dispatchOnUiThread(Runnable action) {
        if (SwingUtilities.isEventDispatchThread()) {
            action.run();
        }
        else {
            SwingUtilities.invokeLater(action);
        }
    }

}
