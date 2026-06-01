package com.acoustic.encoder.features.conversion.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.conversion.ui.swing.binder.action.*;
import com.acoustic.encoder.features.conversion.ui.swing.binder.handler.ParameterComboBoxChangeBindingHandler;
import com.acoustic.encoder.features.conversion.ui.swing.binder.handler.ParameterSliderChangeBindingHandler;
import com.acoustic.encoder.features.conversion.ui.swing.binder.handler.VoiceSelectorClickHandler;
import com.acoustic.encoder.features.conversion.ui.swing.binder.provider.MainTextAreaInputProvider;
import com.acoustic.encoder.features.conversion.ui.swing.binder.validator.InstrumentInputValidator;
import com.acoustic.encoder.features.conversion.ui.swing.components.VoiceSelectorPanel;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizerFactory;
import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpen;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.ButtonClickBindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.FrameWindowBindingHandler;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingConversionViewEventBinder implements SwingConversionViewEventBinder {

    private static final String NULL_EVENT_BUS_ERROR_MSG = "EventBus cannot be null!";
    private static final String NULL_PARAMETERS_SERVICE_ERROR_MSG = "Parameters service cannot be null!";
    private static final String NULL_SYNCHRONIZER_FACTORY_ERROR_MSG = "Synchronizer factory cannot be null!";

    private final static String INVALID_INSTRUMENT_INPUT_WARNING = "Invalid instrument - Last valid instrument set";

    private static final String NULL_COMPONENTS_ERROR_MSG = "Conversion view components cannot be null!";

    private final EventBus eventBus;
    private final SwingConversionViewSynchronizerFactory synchronizerFactory;

    private SwingConversionViewSynchronizer synchronizer;
    private boolean bound = false;
    private ConversionViewSwingComponentsWrapper comps;
    private final List<Runnable> removers = new ArrayList<>();

    public DefaultSwingConversionViewEventBinder(
            EventBus eventBus,
            SwingConversionViewSynchronizerFactory synchronizerFactory
    ) {
        if (eventBus == null) throw new IllegalArgumentException(NULL_EVENT_BUS_ERROR_MSG);
        this.eventBus = eventBus;

        if (synchronizerFactory == null) throw new IllegalArgumentException(NULL_SYNCHRONIZER_FACTORY_ERROR_MSG);
        this.synchronizerFactory = synchronizerFactory;
    }

    @Override
    public SwingConversionViewSynchronizer bind(
            ConversionController controller,
            SwingFrame frame,
            ConversionViewSwingComponentsWrapper components
    ) {
        if (bound) return this.synchronizer;

        if (components == null) throw new IllegalArgumentException(NULL_COMPONENTS_ERROR_MSG);
        this.comps = components;

        this.synchronizer = synchronizerFactory.createSynchronizer(comps);
        bindSynchronizer();

        List<BindingHandler> bindingHandlers = createBindingHandlers(frame, controller);
        for (BindingHandler bindingHandler : bindingHandlers) {
            bindingHandler.bind(removers);
        }

        bound = true;

        return this.synchronizer;
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

    private void bindSynchronizer() {
        EventListener<ProjectReadyToOpen> openListener =
                event ->
                        SwingUtilities.invokeLater(() -> synchronizer.syncMusicProject(event.project()));
        eventBus.subscribe(ProjectReadyToOpen.class, openListener);
        removers.add(() -> eventBus.unsubscribe(ProjectReadyToOpen.class, openListener));
    }

    private List<BindingHandler> createBindingHandlers(
            SwingFrame frame,
            ConversionController controller
    ) {
        List<BindingHandler> handlers = new ArrayList<>();
        handlers.add(new FrameWindowBindingHandler(frame, getFrameExitAction()));
        handlers.addAll(createButtonsBindingHandlers(frame, controller));
        handlers.addAll(createParametersBindingHandlers(frame));

        for (JRadioButton button : comps.voiceSelector().getButtons()) {
            handlers.add(new VoiceSelectorClickHandler(
                    button,
                    getVoiceSelectionAction(button, comps.voiceSelector(), frame))
            );
        }

        return handlers;
    }

    private List<BindingHandler> createParametersBindingHandlers(
            SwingFrame frame
    ) {
        return List.of(
                new ParameterSliderChangeBindingHandler(comps.bpmPanel(), getBpmChangeAction()),
                new ParameterSliderChangeBindingHandler(comps.volumePanel(), getVolumeChangeAction()),
                new ParameterSliderChangeBindingHandler(comps.octavePanel(), getOctaveChangeAction()),
                new ParameterComboBoxChangeBindingHandler(
                        frame,
                        comps.instrumentPanel(),
                        getInstrumentChangeAction(),
                        INVALID_INSTRUMENT_INPUT_WARNING
                )
        );
    }

    private List<BindingHandler> createButtonsBindingHandlers(
            SwingFrame frame,
            ConversionController controller
    ) {
        return List.of(
                new ButtonClickBindingHandler(comps.converterButton(), getConvertAction(frame, controller)),
                new ButtonClickBindingHandler(comps.saveTextButton(), getSaveTextAction(frame, controller)),
                new ButtonClickBindingHandler(comps.loadTextButton(), getLoadTextAction(frame, controller)),
                new ButtonClickBindingHandler(comps.openProjectButton(), getOpenProjectAction(frame, controller)),
                new ButtonClickBindingHandler(comps.saveProjectButton(), getSaveProjectAction(frame, controller))
        );
    }

    private Runnable getFrameExitAction() {
        return new ConversionFrameExitAction(eventBus);
    }

    private Runnable getConvertAction(SwingFrame frame, ConversionController controller) {
        return new ConvertAction(
                frame,
                controller,
                synchronizer,
                new MainTextAreaInputProvider(comps.mainTextAreaPanel()),
                new InstrumentInputValidator(comps.instrumentPanel())
        );
    }

    private Runnable getLoadTextAction(SwingFrame frame, ConversionController controller) {
        return new LoadTextAction(frame, controller, comps.mainTextAreaPanel().getTextAreaUpdater());
    }

    private Runnable getSaveTextAction(SwingFrame frame, ConversionController controller) {
        return new SaveTextAction(frame, controller, new MainTextAreaInputProvider(comps.mainTextAreaPanel()));
    }

    private Runnable getOpenProjectAction(SwingFrame frame, ConversionController controller) {
        return new OpenProjectAction(frame, controller, synchronizer);
    }

    private Runnable getSaveProjectAction(SwingFrame frame, ConversionController controller) {
        return new SaveProjectAction(
                frame,
                controller,
                synchronizer,
                new MainTextAreaInputProvider(comps.mainTextAreaPanel())
        );
    }

    private Runnable getBpmChangeAction() { return () -> synchronizer.syncBpm(); }

    private Runnable getVolumeChangeAction() { return () -> synchronizer.syncVoiceVolume(); }

    private Runnable getOctaveChangeAction() { return () -> synchronizer.syncVoiceOctave(); }

    private Runnable getInstrumentChangeAction() { return () -> synchronizer.syncVoiceInstrument(); }

    private Runnable getVoiceSelectionAction(JRadioButton button, VoiceSelectorPanel panel, SwingFrame frame) {
        return new VoiceSelectionAction(
                button,
                panel,
                frame,
                synchronizer,
                new InstrumentInputValidator(comps.instrumentPanel())
        );
    }
}