package com.acoustic.encoder.features.player.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.ui.swing.binder.action.*;
import com.acoustic.encoder.features.player.ui.swing.binder.handler.ProgressBarManualChangeBindingHandler;
import com.acoustic.encoder.features.player.ui.swing.components.MusicProgressBarPanel;
import com.acoustic.encoder.features.player.ui.swing.components.dto.PlayerViewComponentsWrapper;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizer;
import com.acoustic.encoder.features.player.ui.swing.synchronizer.SwingPlayerViewSynchronizerFactory;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.ButtonClickBindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.FrameWindowBindingHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultSwingPlayerViewEventBinder implements SwingPlayerViewEventBinder {
    private static final String NULL_EVENT_BUS_ERROR_MSG = "EventBus cannot be null!";
    private static final String NULL_COMPONENTS_ERROR_MSG = "Player view components cannot be null!";

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

        if (components == null) throw new IllegalArgumentException(NULL_COMPONENTS_ERROR_MSG);
        this.comps = components;

        this.synchronizer = synchronizerFactory.createSynchronizer(
                comps,
                controller::getMicrosecPosition,
                controller::getMicrosecDuration,
                controller::isPlayingAudio
        );

        List<BindingHandler> bindingHandlers = createBindingHandlers(frame, controller);
        for (BindingHandler bindingHandler : bindingHandlers) {
            bindingHandler.bind(removers);
        }

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

    private List<BindingHandler> createBindingHandlers(SwingFrame frame, AudioPlayerController controller) {
        return List.of(
                new FrameWindowBindingHandler(frame, getFrameExitAction()),
                new ButtonClickBindingHandler(
                        comps.skipMusicBackwardButton(), getSkipBackwardAction(controller)),
                new ButtonClickBindingHandler(
                        comps.skipMusicForwardButton(), getSkipForwardAction(controller)),
                new ButtonClickBindingHandler(
                        comps.saveMusicButton(), getSaveMusicAction(frame, controller)
                ),
                new ButtonClickBindingHandler(
                        comps.playPauseButton(), getPlayPauseAction(controller)
                ),
                new ProgressBarManualChangeBindingHandler(
                        comps.progressBarPanel(),
                        getProgressBarManualChangeAction(comps.progressBarPanel(), controller)
                )
        );
    }

    private Runnable getFrameExitAction() {
        return new PlayerFrameExitAction(eventBus);
    }

    private Runnable getSkipForwardAction(AudioPlayerController controller) {
        return new SkipMusicForwardAction(controller);
    }

    private Runnable getSkipBackwardAction(AudioPlayerController controller) {
        return new SkipMusicBackwardAction(controller);
    }

    private Runnable getSaveMusicAction(SwingFrame frame, AudioPlayerController controller) {
        return new SaveMusicAction(frame, controller);
    }

    private Runnable getPlayPauseAction(AudioPlayerController controller) {
        return new PlayPauseMusicAction(comps.playPauseButton(), controller);
    }

    private Runnable getProgressBarManualChangeAction(
            MusicProgressBarPanel progressPanel,
            AudioPlayerController controller
    ) {
        return new ProgressBarManualChangeAction(progressPanel, controller, synchronizer);
    }
}