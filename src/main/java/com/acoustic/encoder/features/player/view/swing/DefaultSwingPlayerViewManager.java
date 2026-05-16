package com.acoustic.encoder.features.player.view.swing;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.view.PlayerViewManager;
import com.acoustic.encoder.features.player.view.swing.assembler.SwingPlayerViewAssembler;
import com.acoustic.encoder.features.player.view.swing.binder.SwingPlayerViewEventBinder;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DefaultSwingPlayerViewManager implements PlayerViewManager {

    private final static String WINDOW_TITLE = "Music Player";
    private final static int WINDOW_HEIGHT = 200;
    private final static int WINDOW_WIDTH = 400;
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingPlayerViewAssembler assembler;

    private final SwingPlayerViewEventBinder binder;

    private final EventBus eventBus;

    private SwingFrame frame;

    public DefaultSwingPlayerViewManager(
            SwingPlayerViewAssembler assembler,
            SwingPlayerViewEventBinder binder,
            EventBus eventBus
    ) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        this.binder = binder;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

    }

    @Override
    public void startFrame(AudioPlayerController controller) {
        frame = assembler.assembleFrame(
                WINDOW_TITLE,
                WINDOW_WIDTH,
                WINDOW_HEIGHT,
                FRAME_EXIT_OPERATION
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new PlayerClosedEvent());
            }
        });

        binder.bind(controller, frame, assembler.getComponents());

        showFrame();
    }

    @Override
    public void showFrame() { frame.setVisible(true); }

    @Override
    public void hideFrame() { frame.setVisible(false); }

    // void destroyFrame();
}
