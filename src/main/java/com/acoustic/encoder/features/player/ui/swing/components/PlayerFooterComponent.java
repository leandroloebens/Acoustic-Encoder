package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerFooterComponent extends JPanel {

    private final SwingButton saveButton;
    private final SwingSeekBar playbackSeekBar;

    private final SwingLabel currentTimeLabel;
    private final SwingLabel durationTimeLabel;

    public PlayerFooterComponent(
            SwingButton saveButton,
            SwingSeekBar playbackSeekBar,
            SwingLabel currentTimeLabel,
            SwingLabel durationTimeLabel
    ) {
        this.saveButton = saveButton;
        this.playbackSeekBar = playbackSeekBar;
        this.currentTimeLabel = currentTimeLabel;
        this.durationTimeLabel = durationTimeLabel;

        initializeComponent();
    }

    private void initializeComponent() {
        //setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setLayout(new BorderLayout(10, 0));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        SwingUtils.setHandCursor(saveButton, playbackSeekBar);

        //setBackground(Color.BLUE);
        setBackground(new Color(18,18,18));

        JPanel timePanel = new JPanel(new BorderLayout()); // PROTOTIPO -> ARRUMAR
        timePanel.setOpaque(false);

        timePanel.add(currentTimeLabel, BorderLayout.WEST);
        timePanel.add(durationTimeLabel, BorderLayout.EAST);

        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setOpaque(false);

        progressPanel.add(playbackSeekBar, BorderLayout.CENTER);
        progressPanel.add(timePanel, BorderLayout.SOUTH);

        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);

//        add(playbackSeekBar, BorderLayout.CENTER);
        add(progressPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.EAST);

    }

    public SwingButton getSaveButton() {
        return this.saveButton;
    }

    public SwingSeekBar getPlaybackSeekBar() {
        return this.playbackSeekBar;
    }

    public void setProgressTimeLabel(String currentTime, String totalTime) {
        currentTimeLabel.setText(currentTime);
        durationTimeLabel.setText(totalTime);
    }
}
