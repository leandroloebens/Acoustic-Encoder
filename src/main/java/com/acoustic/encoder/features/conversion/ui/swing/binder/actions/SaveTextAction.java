package com.acoustic.encoder.features.conversion.ui.swing.binder.actions;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.ui.swing.binder.provider.TextInputProvider;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;
import java.io.IOException;

public class SaveTextAction implements Runnable {

    private static final String SAVE_TEXT_FILE_EXTENSION_FILTER = "txt";
    private static final String SAVE_TEXT_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String SAVE_TEXT_DIALOG_TITLE = "Save as";

    private final SwingFrame frame;
    private final ConversionController controller;
    private final TextInputProvider textProvider;

    public SaveTextAction(SwingFrame frame, ConversionController controller, TextInputProvider textProvider) {
        if (frame == null) throw new IllegalArgumentException("Frame cannot be null");
        this.frame = frame;

        if (controller == null) throw new IllegalArgumentException("Controller cannot be null");
        this.controller = controller;

        if (textProvider == null) throw new IllegalArgumentException("TextProvider cannot be null");
        this.textProvider = textProvider;
    }

    @Override
    public void run() {
        File fileToSave = SwingUtils.getFileFromChooser(
                SwingUtils.SAVE_FILE_OPERATION,
                frame,
                SAVE_TEXT_FILE_EXTENSION_FILTER,
                SAVE_TEXT_FILTER_DESCRIPTION,
                SAVE_TEXT_DIALOG_TITLE
        );

        if (fileToSave != null) {
            try {
                controller.handleSaveTextAction(textProvider.getTextInput(), fileToSave);
                SwingUtils.showMessage(frame, "Saved!");
            } catch (IOException ex) {
                SwingUtils.showErrorMessage(frame, "Error saving file: " + ex.getMessage());
            }
        }
    }
}
