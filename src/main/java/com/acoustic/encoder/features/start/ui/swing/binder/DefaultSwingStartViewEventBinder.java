package com.acoustic.encoder.features.start.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.domain.event.ProjectReadyToOpen;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.ui.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingStartViewEventBinder implements SwingStartViewEventBinder {

    private static final String OPEN_FILE_EXTENSION_FILTER = "aef";
    private static final String OPEN_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String OPEN_DIALOG_TITLE = "Open";
    private static final String LOAD_FILE_ERROR_MSG = "Error loading file: ";
    private static final String LOAD_FILE_ERROR_TITLE = "LOAD ERROR";

    private boolean bound;

    private final List<Runnable> removers = new ArrayList<>();

    public DefaultSwingStartViewEventBinder() {
        this.bound = false;
    }

    @Override
    public void bind(
            StartController controller,
            SwingFrame frame,
            StartViewSwingComponentsWrapper components,
            EventBus eventBus
    ) {
        if (bound) return;

        SwingButton openProjectButton = components.openProjectButton();
        SwingButton newProjectButton = components.newProjectButton();

        bindOpenProjectButton(openProjectButton, controller, frame, eventBus);
        bindNewProjectButton(newProjectButton, controller, frame, eventBus);

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

    private void bindOpenProjectButton(
            SwingButton button,
            StartController controller,
            SwingFrame frame,
            EventBus eventBus
    ) {
        ActionListener openProjectListener = event -> {
            File fileToLoad = SwingUtils.getFileFromChooser(
                    SwingUtils.LOAD_FILE_OPERATION,
                    frame,
                    OPEN_FILE_EXTENSION_FILTER,
                    OPEN_FILTER_DESCRIPTION,
                    OPEN_DIALOG_TITLE
            );

            if (fileToLoad != null) {
                UserConversionInput project = controller.handleOpenProjectAction(fileToLoad);

                if (project != null)
                    eventBus.publish(new ProjectReadyToOpen(project));
                else {
                    JOptionPane.showMessageDialog(
                            frame,
                            LOAD_FILE_ERROR_MSG + fileToLoad.getName(),
                            LOAD_FILE_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        button.addActionListener(openProjectListener);
        removers.add(() -> button.removeActionListener(openProjectListener));
    }

    private void bindNewProjectButton(
            SwingButton button,
            StartController controller,
            SwingFrame frame,
            EventBus eventBus
    ) {
        ActionListener newProjectListener = event -> {
            UserConversionInput project = controller.handleNewProjectAction();
            eventBus.publish(new ProjectReadyToOpen(project));
        };

        button.addActionListener(newProjectListener);
        removers.add(() -> button.removeActionListener(newProjectListener));
    }

}
