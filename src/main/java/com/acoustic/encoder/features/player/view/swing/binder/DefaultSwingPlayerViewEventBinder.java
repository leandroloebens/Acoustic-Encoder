package com.acoustic.encoder.features.player.view.swing.binder;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.model.MusicParametersState;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.view.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.event.EventListener;
import com.acoustic.encoder.shared.event.ProjectReadyToOpen;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

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

        bindPlayButton(controller);
        bindPauseButton(controller);
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

    private void bindPlayButton(AudioPlayerController controller) {
        comps.controlsComponent().getPlayButton().addActionListener(e -> controller.handlePlayAction());
    }

    private void bindPauseButton(AudioPlayerController controller) {
        comps.controlsComponent().getPauseButton().addActionListener(e -> controller.handlePauseAction());
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
