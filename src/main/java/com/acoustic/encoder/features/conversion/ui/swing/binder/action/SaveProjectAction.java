package com.acoustic.encoder.features.conversion.ui.swing.binder.action;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.ui.swing.binder.provider.TextInputProvider;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingMessageUtils;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;
import java.io.IOException;

public class SaveProjectAction implements Runnable {

    private static final String SAVE_PROJECT_FILE_EXTENSION_FILTER = "aef";
    private static final String SAVE_PROJECT_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String SAVE_PROJECT_DIALOG_TITLE = "Save as";

    private final SwingFrame frame;
    private final ConversionController controller;
    private final SwingConversionViewSynchronizer synchronizer;
    private final ConversionParametersService parametersService;
    private final TextInputProvider textProvider;

    public SaveProjectAction(
            SwingFrame frame,
            ConversionController controller,
            ConversionParametersService parametersService,
            SwingConversionViewSynchronizer synchronizer,
            TextInputProvider textProvider
    ) {
        if (frame == null) throw new IllegalArgumentException("Frame may not be null");
        this.frame = frame;

        if (controller == null) throw new IllegalArgumentException("Controller may not be null");
        this.controller = controller;

        if (parametersService == null) throw new IllegalArgumentException("ParametersService may not be null");
        this.parametersService = parametersService;

        if (synchronizer == null) throw new IllegalArgumentException("Synchronizer may not be null");
        this.synchronizer = synchronizer;

        if (textProvider == null) throw new IllegalArgumentException("TextProvider cannot be null");
        this.textProvider = textProvider;
    }


    @Override
    public void run() {
        File fileToSave = SwingUtils.getFileFromChooser(
                SwingUtils.SAVE_FILE_OPERATION,
                frame,
                SAVE_PROJECT_FILE_EXTENSION_FILTER,
                SAVE_PROJECT_FILTER_DESCRIPTION,
                SAVE_PROJECT_DIALOG_TITLE
        );

        if (fileToSave != null) {
            try {
                MusicProject project = parametersService.wrapMusicProject(
                        textProvider.getTextInput(), synchronizer.getParameters());

                controller.handleSaveProjectAction(project, fileToSave);

                SwingMessageUtils.showMessage(frame, "Saved!");
            } catch (IOException ex) {
                SwingMessageUtils.showErrorMessage(frame, "Error saving project file: " + ex.getMessage());
            }
        }
    }

}
