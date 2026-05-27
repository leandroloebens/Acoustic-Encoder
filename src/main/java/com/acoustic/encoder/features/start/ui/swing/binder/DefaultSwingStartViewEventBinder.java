package com.acoustic.encoder.features.start.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.ui.swing.binder.action.NewProjectAction;
import com.acoustic.encoder.features.start.ui.swing.binder.action.StartFrameExitAction;
import com.acoustic.encoder.features.start.ui.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.features.start.ui.swing.binder.action.OpenProjectAction;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.BindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.ButtonClickBindingHandler;
import com.acoustic.encoder.infrastructure.ui_shared.swing.handler.FrameWindowBindingHandler;

import java.util.ArrayList;
import java.util.List;

public class DefaultSwingStartViewEventBinder implements SwingStartViewEventBinder {

    private final EventBus eventBus;

    private boolean bound;

    private final List<Runnable> removers = new ArrayList<>();

    public DefaultSwingStartViewEventBinder(EventBus eventBus) {
        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

        this.bound = false;
    }

    @Override
    public void bind(
            StartController controller,
            SwingFrame frame,
            StartViewSwingComponentsWrapper components
    ) {
        if (bound) return;

        List<BindingHandler> handlers = createBindingHandlers(components, controller, frame);

        for (BindingHandler handler : handlers) {
            handler.bind(removers);
        }

        bound = true;
    }

    @Override
    public void unbind() {
        if (!bound) return;

        for (Runnable remove : removers) {
            remove.run();
        }

        removers.clear();

        bound = false;
    }

    private List<BindingHandler> createBindingHandlers(
            StartViewSwingComponentsWrapper comps,
            StartController controller,
            SwingFrame frame
    ) {
        return List.of(
                new FrameWindowBindingHandler(frame, getFrameExitAction()),
                new ButtonClickBindingHandler(comps.newProjectButton(), getNewProjectButtonAction(controller)),
                new ButtonClickBindingHandler(comps.openProjectButton(), getOpenProjectAction(controller, frame))
        );
    }

    private Runnable getFrameExitAction() {
        return new StartFrameExitAction(eventBus);
    }

    private Runnable getOpenProjectAction(StartController controller, SwingFrame frame) {
        return new OpenProjectAction(frame, controller, eventBus);
    }

    private Runnable getNewProjectButtonAction(StartController controller) {
        return new NewProjectAction(controller, eventBus);
    }
}