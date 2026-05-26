package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.*;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerFooterComponent extends JPanel {

    private final static int FOOTER_PANEL_HGAP = (int) (12 * SwingUtils.getScreenScaleRatio());
    private final static int FOOTER_PANEL_VGAP = (int) (0 * SwingUtils.getScreenScaleRatio());

    private final static int FOOTER_PANEL_TGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int FOOTER_PANEL_LGAP = (int) (20 * SwingUtils.getScreenScaleRatio());
    private final static int FOOTER_PANEL_BGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int FOOTER_PANEL_RGAP = (int) (20 * SwingUtils.getScreenScaleRatio());

    private final static int SAVE_WRAPPER_TGAP = (int) (0 * SwingUtils.getScreenScaleRatio());
    private final static int SAVE_WRAPPER_LGAP = (int) (8 * SwingUtils.getScreenScaleRatio());
    private final static int SAVE_WRAPPER_BGAP = (int) (14 * SwingUtils.getScreenScaleRatio());
    private final static int SAVE_WRAPPER_RGAP = (int) (0 * SwingUtils.getScreenScaleRatio());

    private final static Color BACKGROUND_COLOR = new Color(18, 18, 18);

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
        setLayout(new BorderLayout(FOOTER_PANEL_HGAP, FOOTER_PANEL_VGAP));
        setBorder(BorderFactory.createEmptyBorder(
                FOOTER_PANEL_TGAP,
                FOOTER_PANEL_LGAP,
                FOOTER_PANEL_BGAP,
                FOOTER_PANEL_RGAP
        ));

        SwingUtils.setHandCursor(saveButton, playbackSeekBar);

        setBackground(BACKGROUND_COLOR);

        SwingPanel timePanel = new SwingPanel(new BorderLayout());
        timePanel.setOpaque(false);

        timePanel.add(currentTimeLabel, BorderLayout.WEST);
        timePanel.add(durationTimeLabel, BorderLayout.EAST);

        SwingPanel progressPanel = new SwingPanel(new BorderLayout());
        progressPanel.setOpaque(false);

        progressPanel.add(playbackSeekBar, BorderLayout.CENTER);
        progressPanel.add(timePanel, BorderLayout.SOUTH);

        SwingPanel saveButtonWrapper = new SwingPanel(new GridBagLayout());;
        saveButtonWrapper.setOpaque(false);
        saveButtonWrapper.setBorder(BorderFactory.createEmptyBorder(
                SAVE_WRAPPER_TGAP,
                SAVE_WRAPPER_LGAP,
                SAVE_WRAPPER_BGAP,
                SAVE_WRAPPER_RGAP
        ));

        saveButtonWrapper.add(saveButton);

        add(progressPanel, BorderLayout.CENTER);
        add(saveButtonWrapper, BorderLayout.EAST);

        initializeSaveButton();
        initializeSeekBar();
    }

    private void initializeSaveButton() {
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
    }

    private void initializeSeekBar() {
        playbackSeekBar.setUI(new ProgressSliderUI());
        playbackSeekBar.setPaintTicks(false);
        playbackSeekBar.setPaintLabels(false);
        playbackSeekBar.setOpaque(false);
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
