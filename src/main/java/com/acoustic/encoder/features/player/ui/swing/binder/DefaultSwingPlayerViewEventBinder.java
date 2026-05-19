package com.acoustic.encoder.features.player.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingPlayerViewEventBinder implements SwingPlayerViewEventBinder {
    private static final String NULL_EVENT_BUS_ERROR_MSG = "EventBus cannot be null!";

    private static final String ONSAVE_FILE_EXTENSION_FILTER = "mid";
    private static final String ONSAVE_FILTER_DESCRIPTION = "MID Files (*.mid)";
    private static final String ONSAVE_DIALOG_TITLE = "Save as";

    private final EventBus eventBus;

    private boolean bound = false;

    private PlayerViewComponentsWrapper comps;

    private final List<Runnable> removers = new ArrayList<>();

    private boolean isPlaying = false;

    public DefaultSwingPlayerViewEventBinder(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException(NULL_EVENT_BUS_ERROR_MSG);
        this.eventBus = eventBus;

    }

    @Override
    public void bind(
            AudioPlayerController controller,
            SwingFrame frame,
            PlayerViewComponentsWrapper components
    ) {
        if (bound) return;

        this.comps = components;

        bindPlayPauseButton(controller);
        bindRewindButton(controller);
        bindSaveButton(frame, controller);

        bound = true;
    }

    @Override
    public void unbind() {
        if (!bound) return;

        for (Runnable remove : removers) {
            remove.run();
        }

        removers.clear();
        comps = null;

        bound = false;
    }

    private void bindPlayPauseButton(AudioPlayerController controller) {
//        comps.controlsComponent().getPlayPauseButton().addActionListener(e -> {
//            if (isPlaying) {
//                controller.handlePauseAction();
//                comps.controlsComponent().getPlayPauseButton().setText("Play");
//                comps.controlsComponent().getPlayPauseButton().setIcon(playIcon);
//            } else {
//                controller.handlePlayAction();
//                comps.controlsComponent().getPlayPauseButton().setText("Pause");
//                comps.controlsComponent().getPlayPauseButton().setIcon(pauseIcon);
//            }
//
//            isPlaying = !isPlaying;
//        });

        comps.controlsComponent().getPlayPauseButton().addActionListener(e -> {
            if (isPlaying) {
                controller.handlePauseAction();
                comps.controlsComponent().setPlayPauseState(isPlaying);
            } else {
                controller.handlePlayAction();
                comps.controlsComponent().setPlayPauseState(isPlaying);
            }

            isPlaying = !isPlaying;
        });
    }

    private void bindRewindButton(AudioPlayerController controller) {
        comps.controlsComponent().getRewindButton().addActionListener(e -> controller.handleRewindAction());
    }

    private void bindSaveButton(SwingFrame frame, AudioPlayerController controller) {
        comps.footerComponent().getSaveButton().addActionListener(e -> {
            File fileToSave = SwingUtils.getFileFromChooser(
                    SwingUtils.SAVE_FILE_OPERATION,
                    frame,
                    ONSAVE_FILE_EXTENSION_FILTER,
                    ONSAVE_FILTER_DESCRIPTION,
                    ONSAVE_DIALOG_TITLE
            );

            if (fileToSave != null) {

                try {
                    controller.handleSaveAction(fileToSave);
                } catch (MusicExportException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
}
