package com.acoustic.encoder.features.conversion.ui.swing.binder.action;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingMessageUtils;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;
import java.io.IOException;

public class OpenProjectAction implements Runnable {

    private static final String OPEN_PROJECT_FILE_EXTENSION_FILTER = "aef";
    private static final String OPEN_PROJECT_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String OPEN_PROJECT_DIALOG_TITLE = "Open";

    private final SwingFrame frame;
    private final ConversionController controller;
    private final SwingConversionViewSynchronizer synchronizer;

    public OpenProjectAction(
            SwingFrame frame,
            ConversionController controller,
            SwingConversionViewSynchronizer synchronizer
    ) {
        if (frame == null) throw new IllegalArgumentException("Frame must not be null");
        this.frame = frame;

        if (controller == null) throw new IllegalArgumentException("Controller must not be null");
        this.controller = controller;

        if (synchronizer == null) throw new IllegalArgumentException("Synchronizer must not be null");
        this.synchronizer = synchronizer;
    }

    @Override
    public void run() {
        File fileToLoad = SwingUtils.getFileFromChooser(
                SwingUtils.LOAD_FILE_OPERATION,
                frame,
                OPEN_PROJECT_FILE_EXTENSION_FILTER,
                OPEN_PROJECT_FILTER_DESCRIPTION,
                OPEN_PROJECT_DIALOG_TITLE
        );

        if (fileToLoad != null) {
            try {
                MusicProject loadedProject = controller.handleLoadProjectAction(fileToLoad);
                synchronizer.syncMusicProject(loadedProject);
            } catch (IOException | IllegalArgumentException e) {
                SwingMessageUtils.showErrorMessage(frame, "Error while loading project: " + e.getMessage());
            }
        }
    }
}
