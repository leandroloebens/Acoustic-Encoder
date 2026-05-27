package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PlayPauseButton extends SwingButton {

    private final Icon playIcon;
    private final Icon pauseIcon;

    public PlayPauseButton(
            String text, Font font, int fontSize, Border border, Dimension size, Icon playIcon, Icon pauseIcon
    ) {
        if (playIcon == null) throw new IllegalArgumentException("playIcon cannot be null");
        this.playIcon = playIcon;
        super(text, font, fontSize, playIcon, border, size);

        if (pauseIcon == null) throw new IllegalArgumentException("pauseIcon cannot be null");
        this.pauseIcon = pauseIcon;
    }

    public void setPlayPauseState(boolean isPlaying) {
        setIcon(isPlaying ? pauseIcon : playIcon);
    }
}
