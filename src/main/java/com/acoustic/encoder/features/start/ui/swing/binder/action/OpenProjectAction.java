package com.acoustic.encoder.features.start.ui.swing.binder.action;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpenEvent;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingMessageUtils;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;
import java.io.IOException;

public class OpenProjectAction implements Runnable {

    private static final String OPEN_FILE_EXTENSION_FILTER = "aef";
    private static final String OPEN_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String OPEN_DIALOG_TITLE = "Open";

    private final SwingFrame frame;
    private final EventBus eventBus;
    private final StartController controller;


    public OpenProjectAction(SwingFrame frame, StartController controller, EventBus eventBus) {
        if (frame == null) throw new IllegalArgumentException("Frame must not be null");
        this.frame = frame;

        if  (controller == null) throw new IllegalArgumentException("Controller must not be null");
        this.controller = controller;

        if  (eventBus == null) throw new IllegalArgumentException("EventBus must not be null");
        this.eventBus = eventBus;
    }

    @Override
    public void run() {
        File fileToLoad = SwingUtils.getFileFromChooser(
                SwingUtils.LOAD_FILE_OPERATION,
                frame,
                OPEN_FILE_EXTENSION_FILTER,
                OPEN_FILTER_DESCRIPTION,
                OPEN_DIALOG_TITLE
        );

        if (fileToLoad != null) {
            try {
                MusicProject project = controller.handleOpenProjectAction(fileToLoad);
                eventBus.publish(new ProjectReadyToOpenEvent(project));
            } catch (IOException | IllegalArgumentException e) {
                SwingMessageUtils.showErrorMessage(frame, "Error while loading project: " + e.getMessage());
            }
        }
    }
}
