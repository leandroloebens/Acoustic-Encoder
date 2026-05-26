package com.acoustic.encoder.features.start.ui.swing.binder.action;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpen;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;

public class OpenProjectAction implements Runnable {

    private static final String OPEN_FILE_EXTENSION_FILTER = "aef";
    private static final String OPEN_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String OPEN_DIALOG_TITLE = "Open";
    private static final String LOAD_FILE_ERROR_MSG = "Error loading file: ";

    private final SwingFrame frame;
    private final EventBus eventBus;
    private final StartController controller;


    public OpenProjectAction(SwingFrame frame, StartController controller, EventBus eventBus) {
        if (frame == null) throw new IllegalArgumentException("Frame must not be null");
        this.frame = frame;

        if  (controller == null) throw new IllegalArgumentException("Controller must not be null");
        this.eventBus = eventBus;

        if  (eventBus == null) throw new IllegalArgumentException("EventBus must not be null");
        this.controller = controller;
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
            MusicProject project = controller.handleOpenProjectAction(fileToLoad);

            if (project != null)
                eventBus.publish(new ProjectReadyToOpen(project));
            else {
                SwingUtils.showErrorMessage(
                        frame,
                        LOAD_FILE_ERROR_MSG + fileToLoad.getName()
                );
            }
        }
    }
}
