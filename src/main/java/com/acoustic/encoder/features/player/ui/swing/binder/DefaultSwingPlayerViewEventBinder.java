package com.acoustic.encoder.features.player.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.event.PlayerClosedEvent;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizer;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizerFactory;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private final SwingPlayerViewSynchronizerFactory synchronizerFactory;
    private final List<Runnable> removers = new ArrayList<>();

    private PlayerViewComponentsWrapper comps;
    private SwingPlayerViewSynchronizer synchronizer;
    private boolean bound = false;

    public DefaultSwingPlayerViewEventBinder(
            EventBus eventBus,
            SwingPlayerViewSynchronizerFactory synchronizerFactory
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
        if (bound) return this.synchronizer;

        this.comps = components;

        bindPlayPauseButton(controller);
        bindSkipBackwardButton(controller);
        bindSkipForwardButton(controller);
        bindSaveButton(frame, controller);
        bindPlaybackSeekBar(comps.footerComponent().getPlaybackSeekBar(), controller);

        bindFrameExit(frame);

        this.synchronizer = synchronizerFactory.createSynchronizer(
                comps,
                controller::getMicrosecPosition,
                controller::getMicrosecDuration,
                controller::isPlayingAudio
        );

        bound = true;

        return synchronizer;
    }

    @Override
    public void unbind() {
        if (!bound) return;

        for (Runnable remove : removers) {
            remove.run();
        }

        removers.clear();
        comps = null;
        synchronizer = null;

        bound = false;
    }

    private void bindFrameExit(SwingFrame frame) {
        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                eventBus.publish(new PlayerClosedEvent());
            }
        };

        frame.addWindowListener(windowAdapter);
        removers.add(() -> frame.removeWindowListener(windowAdapter));
    }

    private void bindPlayPauseButton(AudioPlayerController controller) {
        ActionListener pauseListener = event -> {
            controller.handlePlayPauseToggleAction();
            comps.controlsComponent().setPlayPauseState(controller.isPlayingAudio());
        };

        comps.controlsComponent().getPlayPauseButton().addActionListener(pauseListener);
        removers.add(() -> comps.controlsComponent().getPlayPauseButton().removeActionListener(pauseListener));
    }

    private void bindSkipBackwardButton(AudioPlayerController controller) {
        comps.controlsComponent().getSkipBackwardButton().addActionListener(e ->
                controller.handleSkipBackwardAction());
    }

    private void bindSkipForwardButton(AudioPlayerController controller) {
        comps.controlsComponent().getSkipForwardButton().addActionListener(e ->
                controller.handleSkipForwardAction());
    }

    private void bindSaveButton(SwingFrame frame, AudioPlayerController controller) {
        ActionListener saveListener = event -> {
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
                    SwingUtils.showErrorMessage(frame, ex.getMessage());
                }

            }
        };

        comps.footerComponent().getSaveButton().addActionListener(saveListener);
        removers.add(() -> comps.footerComponent().getSaveButton().removeActionListener(saveListener));
    }

    private void bindPlaybackSeekBar(SwingSeekBar playbackSeekBar, AudioPlayerController controller) {
        ChangeListener changeListener = event -> {
            if (synchronizer != null && synchronizer.isUpdatingProgrammatically()) {
                return;
            }

            JSlider source = (JSlider) event.getSource();

            if (source.hasFocus() && !playbackSeekBar.getValueIsAdjusting()) {
                source.transferFocus();

                double percentage = (double) playbackSeekBar.getValue() / playbackSeekBar.getMaximum();
                long microsecPosition = (long) (percentage * controller.getMicrosecDuration());
                controller.handleSeekAction(microsecPosition);
            }
        };

        playbackSeekBar.addChangeListener(changeListener);
        removers.add(() -> playbackSeekBar.removeChangeListener(changeListener));
    }
}
