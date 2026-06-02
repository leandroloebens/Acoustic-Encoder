package com.acoustic.encoder.infrastructure.ui_shared;

public interface UiThreadDispatcher {

    void dispatchOnUiThread(Runnable action);

}
