package com.acoustic.encoder.features.start.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpen;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.event.StartScreenCloseRequestEvent;
import com.acoustic.encoder.features.start.ui.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingStartViewEventBinder implements SwingStartViewEventBinder {

    private static final String OPEN_FILE_EXTENSION_FILTER = "aef";
    private static final String OPEN_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String OPEN_DIALOG_TITLE = "Open";
    private static final String LOAD_FILE_ERROR_MSG = "Error loading file: ";
    private static final String LOAD_FILE_ERROR_TITLE = "LOAD ERROR";

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

        SwingButton openProjectButton = components.openProjectButton();
        SwingButton newProjectButton = components.newProjectButton();

        bindOpenProjectButton(openProjectButton, controller, frame);
        bindNewProjectButton(newProjectButton, controller);

        bindFrameExit(frame);

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

    private void bindFrameExit(SwingFrame frame) {
        WindowAdapter windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new StartScreenCloseRequestEvent());
                frame.setVisible(false);
            }
        };

        frame.addWindowListener(windowListener);
        removers.add(() -> frame.removeWindowListener(windowListener));
    }

    private void bindOpenProjectButton(
            SwingButton button,
            StartController controller,
            SwingFrame frame
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
                MusicProject project = controller.handleOpenProjectAction(fileToLoad);

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
            StartController controller
    ) {
        ActionListener newProjectListener = event -> {
            MusicProject project = controller.handleNewProjectAction();
            eventBus.publish(new ProjectReadyToOpen(project));
        };

        button.addActionListener(newProjectListener);
        removers.add(() -> button.removeActionListener(newProjectListener));
    }

}
