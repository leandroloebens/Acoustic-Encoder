package com.acoustic.encoder.features.player.ui.swing.assembler;

import com.acoustic.encoder.features.player.ui.swing.assembler.model.PlayerControlPanelModel;
import com.acoustic.encoder.features.player.ui.swing.assembler.model.PlayerFooterPanelModel;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingPlayerViewAssembler implements SwingPlayerViewAssembler {

    private final static int BORDERLAYOUT_HGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int BORDERLAYOUT_VGAP = (int) (10 * SwingUtils.getScreenScaleRatio());

    private final static int BASE_MAIN_CONTAINER_WIDTH = (int) (350 * SwingUtils.getScreenScaleRatio());
    private final static int BASE_MAIN_CONTAINER_HEIGHT = (int) (150 * SwingUtils.getScreenScaleRatio());

    private final static Color BACKGROUND_COLOR = new Color(18, 18, 18);

    private final PlayerViewComponentsWrapper comps;

    public DefaultSwingPlayerViewAssembler(PlayerViewComponentsWrapper components) {
        if (components == null) throw new IllegalArgumentException("PlayerViewComponentsWrapper cannot be null");
        this.comps = components;
    }

    @Override
    public SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation
    ) {

        SwingFrame frame = new SwingFrame(title, new Dimension(windowWidth, windowHeight), frameExitOperation);

        frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
        frame.setResizable(false);
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_VGAP));

        SwingPanel centerPanel = new SwingPanel(new GridBagLayout());

        SwingPanel mainContainer = new SwingPanel();

        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));

        mainContainer.setPreferredSize(new Dimension(BASE_MAIN_CONTAINER_WIDTH, BASE_MAIN_CONTAINER_HEIGHT));

        SwingPanel controlsPanel = new PlayerControlPanelModel(
                comps.playPauseButton(),
                comps.skipMusicBackwardButton(),
                comps.skipMusicForwardButton()
        );

        SwingPanel footerPanel = new PlayerFooterPanelModel(
                comps.saveMusicButton(),
                comps.progressBarPanel()
        );

        controlsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContainer.add(controlsPanel);
        mainContainer.add(footerPanel);

        mainContainer.setBackground(BACKGROUND_COLOR);

        centerPanel.add(mainContainer);
        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);

        return frame;
    }

    @Override
    public PlayerViewComponentsWrapper getComponents() {
        return comps;
    }
}
