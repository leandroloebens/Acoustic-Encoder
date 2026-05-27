package com.acoustic.encoder.features.player.ui.swing.assembler.model;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingRoundedPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.awt.*;

public class PlayerControlPanelModel extends SwingPanel {

    private static final int CONTROLS_BUTTONS_HGAP = (int) (35 * SwingUtils.getScreenScaleRatio());
    private static final int CONTROLS_BUTTONS_VGAP = (int) (10 * SwingUtils.getScreenScaleRatio());

    private static final int PLAY_PAUSE_RADIUS = (int) (70 * SwingUtils.getScreenScaleRatio());

    private final static Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private static final Color PLAY_PAUSE_BACKGROUND = new Color(200, 200, 200);

    private final SwingButton playPauseButton;
    private final SwingButton skipBackwardButton;
    private final SwingButton skipForwardButton;

    public PlayerControlPanelModel(
            SwingButton playPauseButton,
            SwingButton skipBackwardButton,
            SwingButton skipForwardButton
    ) {

        this.playPauseButton = playPauseButton;
        this.skipBackwardButton = skipBackwardButton;
        this.skipForwardButton = skipForwardButton;

        assemblePanel();
    }

    private void assemblePanel() {
        setLayout(new BorderLayout());

        setBackground(BACKGROUND_COLOR);

        SwingPanel playPauseWrapper = new SwingRoundedPanel(PLAY_PAUSE_RADIUS);
        playPauseWrapper.setBackground(PLAY_PAUSE_BACKGROUND);
        playPauseWrapper.setLayout(new GridBagLayout());
        playPauseWrapper.add(playPauseButton);

        SwingPanel buttonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, CONTROLS_BUTTONS_HGAP, CONTROLS_BUTTONS_VGAP));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(skipBackwardButton);
        buttonsPanel.add(playPauseWrapper);
        buttonsPanel.add(skipForwardButton);

        add(buttonsPanel, BorderLayout.CENTER);
    }
}
