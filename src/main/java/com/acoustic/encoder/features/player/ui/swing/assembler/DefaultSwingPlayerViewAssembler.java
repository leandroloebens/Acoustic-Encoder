package com.acoustic.encoder.features.player.ui.swing.assembler;

import com.acoustic.encoder.features.player.ui.swing.components.PlayerControlsComponent;
import com.acoustic.encoder.features.player.ui.swing.components.PlayerFooterComponent;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingPlayerViewAssembler implements SwingPlayerViewAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

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
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setMaximumSize(new Dimension(1000, 1000));

        controlsComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContainer.add(controlsComponent);
        mainContainer.add(Box.createVerticalStrut(8));
        mainContainer.add(footerComponent);

        mainContainer.setBackground(new Color(18,18,18));

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
