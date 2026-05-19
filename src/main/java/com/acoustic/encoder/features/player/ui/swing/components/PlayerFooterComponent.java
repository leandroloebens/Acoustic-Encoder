package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerFooterComponent extends JPanel {

    private final SwingSeekBar playbackSeekBar;

    private final SwingButton saveButton;

    public PlayerFooterComponent(
            SwingButton saveButton,
            SwingSeekBar playbackSeekBar
    ) {
        this.saveButton = saveButton;
        this.playbackSeekBar = playbackSeekBar;

        initializeComponent();
    }

    private void initializeComponent() {
        //setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        SwingUtils.setHandCursor(saveButton);

        //setBackground(Color.BLUE);
        setBackground(new Color(18,18,18));

        add(playbackSeekBar, BorderLayout.CENTER);
        add(saveButton, BorderLayout.EAST);

    }

    public SwingButton getSaveButton() {
        return this.saveButton;
    }

    public SwingSeekBar getPlaybackSeekBar() {
        return this.playbackSeekBar;
    }

}
