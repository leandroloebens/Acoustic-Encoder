package com.acoustic.encoder.features.player.ui.swing.assembler;

import com.acoustic.encoder.features.player.ui.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.ui.swing.components.PlayerFooterComponent;
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

    private final PlayerControlsComponent controlsComponent;
    private final PlayerFooterComponent footerComponent;

    public DefaultSwingPlayerViewAssembler(PlayerViewComponentsWrapper components) {
        this.controlsComponent = components.controlsComponent();
        this.footerComponent = components.footerComponent();
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

        controlsComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContainer.add(controlsComponent);
        mainContainer.add(footerComponent);

        mainContainer.setBackground(BACKGROUND_COLOR);

        centerPanel.add(mainContainer);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        return frame;
    }

    @Override
    public PlayerViewComponentsWrapper getComponents() {
        return new PlayerViewComponentsWrapper(
                controlsComponent,
                footerComponent
        );
    }
}
