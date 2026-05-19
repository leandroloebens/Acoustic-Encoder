package com.acoustic.encoder.features.player.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizer;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizerFactory;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultSwingPlayerViewEventBinder implements SwingPlayerViewEventBinder {
    private static final String NULL_EVENT_BUS_ERROR_MSG = "EventBus cannot be null!";

    private static final String ONSAVE_FILE_EXTENSION_FILTER = "mid";
    private static final String ONSAVE_FILTER_DESCRIPTION = "MID Files (*.mid)";
    private static final String ONSAVE_DIALOG_TITLE = "Save as";

    private final EventBus eventBus;

    private boolean bound = false;

    private PlayerViewComponentsWrapper comps;
    private SwingPlayerViewSynchronizerFactory synchronizerFactory;

    private final List<Runnable> removers = new ArrayList<>();

    private boolean isPlaying = false;

    public DefaultSwingPlayerViewEventBinder(
            EventBus eventBus, SwingPlayerViewSynchronizerFactory synchronizerFactory
    ) {
        Objects.requireNonNull(eventBus, NULL_EVENT_BUS_ERROR_MSG);
        Objects.requireNonNull(synchronizerFactory, "SynchronizerFactory cannot be null!");

        this.eventBus = eventBus;
        this.synchronizerFactory = synchronizerFactory;
    }

    @Override
    public SwingPlayerViewSynchronizer bind(
            AudioPlayerController controller,
            SwingFrame frame,
            PlayerViewComponentsWrapper components
    ) {
        if (bound) return null;

        this.comps = components;

        bindPlayPauseButton(controller);
        bindRewindButton(controller);
        bindSaveButton(frame, controller);
        bindPlaybackSeekBar(comps.footerComponent().getPlaybackSeekBar(), controller);

        bound = true;

        return synchronizerFactory.createSynchronizer(
                comps,
                controller::getMicrosecPosition,
                controller::getMicrosecDuration
        );
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

    private void bindPlaybackSeekBar(SwingSeekBar playbackSeekBar, AudioPlayerController controller) {

        playbackSeekBar.addChangeListener(e -> {

            if (!playbackSeekBar.getValueIsAdjusting()) {
                controller.handleSeekAction(playbackSeekBar.getValue());
            }

        });


    }
}
