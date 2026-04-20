package com.acoustic.encoder.shared.view;

public interface Button {

    void setPosition(int x, int y);

    void setBorders();

    void setSize();

    void onClick(Runnable clickHandler);
}
