package com.acoustic.encoder.features.player.view;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.domain.music.MusicModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultPlayerScreen implements PlayerScreen {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static int BUTTON_PANEL_TGAP = 10;
    private final static int BUTTON_PANEL_LGAP = 10;
    private final static int BUTTON_PANEL_BGAP = 10;
    private final static int BUTTON_PANEL_RGAP = 10;

    private final JFrame frame;

    private final AudioPlayerController playerController;

    private final PlayerViewManager manager;

    private final EventBus eventBus;

    public DefaultPlayerScreen(AudioPlayerController playerController, PlayerViewManager manager, EventBus eventBus) {

        if (playerController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.playerController = playerController;

        if (manager == null) throw new IllegalArgumentException("Manager cannot be null!");
        this.manager = manager;

        this.eventBus = eventBus;

        this.frame = new JFrame(WINDOW_TITLE);
        this.initializeFrame();

    }

    public void initializeFrame() {

        // Sets the window to close when the user clicks the close button.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Divides the window in NORTH, SOUTH, EAST, WEST and CENTER.
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));
        //frame.setLayout(new GridBagLayout());

        // Adding components to the frame
//        JPanel centerPanel = new JPanel(new GridBagLayout());
//
//        JPanel mainContainer = new JPanel();
//        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
//        mainContainer.setMaximumSize(new Dimension(1000, 1000));
//
//        PlayerControlsComponent controlsComponent = new PlayerControlsComponent(playerController);
//        PlayerFooterComponent footerComponent = new PlayerFooterComponent(playerController);
//
//        controlsComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
//        footerComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        mainContainer.add(controlsComponent);
//        mainContainer.add(Box.createVerticalStrut(8));
//        mainContainer.add(footerComponent);
//
//        mainContainer.setBackground(Color.darkGray);
//
//        centerPanel.add(mainContainer);
//        frame.add(centerPanel, BorderLayout.CENTER);

        // Centering the frame
//        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new PlayerClosedEvent());
            }
        });

    }

    @Override
    public void startFrame() {
        //this.frame.setVisible(true);
        this.manager.startFrame(this.playerController);
    }

    @Override
    public void closeFrame() {
        //this.frame.setVisible(false);
        this.manager.hideFrame();
    }

    @Override
    public void loadMusic(MusicModel musicModel) {
        this.playerController.handleLoadAction(musicModel);
    }
}
