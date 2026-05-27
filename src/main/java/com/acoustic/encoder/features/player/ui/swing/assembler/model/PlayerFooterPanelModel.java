package com.acoustic.encoder.features.player.ui.swing.assembler.model;

import com.acoustic.encoder.features.player.ui.swing.components.MusicProgressBarPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.*;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class PlayerFooterPanelModel extends SwingPanel {

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
    private final MusicProgressBarPanel progressPanel;

    public PlayerFooterPanelModel(
            SwingButton saveButton,
            MusicProgressBarPanel progressBarPanel
    ) {
        this.saveButton = saveButton;
        this.progressPanel = progressBarPanel;

        assemblePanel();
    }

    private void assemblePanel() {
        setLayout(new BorderLayout(FOOTER_PANEL_HGAP, FOOTER_PANEL_VGAP));
        setBorder(BorderFactory.createEmptyBorder(
                FOOTER_PANEL_TGAP,
                FOOTER_PANEL_LGAP,
                FOOTER_PANEL_BGAP,
                FOOTER_PANEL_RGAP
        ));

        setBackground(BACKGROUND_COLOR);

        SwingPanel saveButtonWrapper = new SwingPanel(new GridBagLayout());
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
    }
}
