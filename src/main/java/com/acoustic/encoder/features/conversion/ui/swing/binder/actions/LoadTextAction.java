package com.acoustic.encoder.features.conversion.ui.swing.binder.actions;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.ui.swing.binder.updater.TextAreaUpdater;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;
import java.io.File;
import java.io.IOException;

public class LoadTextAction implements Runnable {

    private static final String LOAD_TEXT_FILE_EXTENSION_FILTER = "txt";
    private static final String LOAD_TEXT_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String LOAD_TEXT_DIALOG_TITLE = "Open";

     private final SwingFrame frame;
     private final ConversionController controller;
     private final TextAreaUpdater textUpdater;

     public LoadTextAction(SwingFrame frame, ConversionController controller, TextAreaUpdater textUpdater) {
         if (frame == null) throw new IllegalArgumentException("Frame cannot be null");
         this.frame = frame;

         if (controller == null) throw new IllegalArgumentException("Controller cannot be null");
         this.controller = controller;

         if (textUpdater == null) throw new IllegalArgumentException("TextProvider cannot be null");
         this.textUpdater = textUpdater;
     }

    @Override
    public void run() {
        File fileToLoad = SwingUtils.getFileFromChooser(
                SwingUtils.LOAD_FILE_OPERATION,
                frame,
                LOAD_TEXT_FILE_EXTENSION_FILTER,
                LOAD_TEXT_FILTER_DESCRIPTION,
                LOAD_TEXT_DIALOG_TITLE
        );

        if (fileToLoad != null) {
            try {
                String text = controller.handleLoadTextAction(fileToLoad);
                textUpdater.setText(text);
            } catch (IOException ex) {
                SwingUtils.showErrorMessage(frame, "Error loading file: " + ex.getMessage());
            }
        }
    }
}
