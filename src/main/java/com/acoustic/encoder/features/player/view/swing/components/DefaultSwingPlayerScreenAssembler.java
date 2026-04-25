package com.acoustic.encoder.features.player.view.swing.components;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.view.PlayerControlsComponent;
import com.acoustic.encoder.features.player.view.PlayerFooterComponent;
import com.acoustic.encoder.features.player.view.swing.SwingPlayerActionHandler;
import com.acoustic.encoder.features.player.view.swing.components.dto.PlayerScreenComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSwingPlayerScreenAssembler implements SwingPlayerScreenAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final PlayerControlsComponent controlsComponent;
    private final PlayerFooterComponent footerComponent;

    public DefaultSwingPlayerScreenAssembler(PlayerScreenComponentsWrapper components) {
        this.controlsComponent = components.controlsComponent();
        this.footerComponent = components.footerComponent();
    }

    @Override
    public SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingPlayerActionHandler handler
    ) {

        SwingFrame frame = new SwingFrame(title, new Dimension(windowWidth, windowHeight), frameExitOperation);

        frame.setMinimumSize(new Dimension(windowWidth, windowHeight));
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        JPanel centerPanel = new JPanel(new GridBagLayout());

        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setMaximumSize(new Dimension(1000, 1000));

        controlsComponent.setEventHandler(handler);
        footerComponent.setEventHandler(handler);

        controlsComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerComponent.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContainer.add(controlsComponent);
        mainContainer.add(Box.createVerticalStrut(8));
        mainContainer.add(footerComponent);

        mainContainer.setBackground(Color.darkGray);

        centerPanel.add(mainContainer);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        return frame;
    }

}
