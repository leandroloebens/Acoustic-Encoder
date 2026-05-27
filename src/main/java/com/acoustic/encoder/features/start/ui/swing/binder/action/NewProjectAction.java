package com.acoustic.encoder.features.start.ui.swing.binder.action;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpen;

public class NewProjectAction implements Runnable {

    private final StartController controller;
    private final EventBus eventBus;

    public NewProjectAction(StartController controller, EventBus eventBus) {
        if (controller == null) throw new IllegalArgumentException("Start controller cannot be null");
        this.controller =  controller;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null");
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        MusicProject project = controller.handleNewProjectAction();
        eventBus.publish(new ProjectReadyToOpen(project));
    }
}
