package com.acoustic.encoder.features.player.ui.swing.binder.action;

import com.acoustic.encoder.features.player.controller.AudioPlayerController;
import com.acoustic.encoder.features.player.exception.MusicExportException;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingMessageUtils;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import java.io.File;

public class SaveMusicAction implements Runnable {

    private static final String ONSAVE_FILE_EXTENSION_FILTER = "mid";
    private static final String ONSAVE_FILTER_DESCRIPTION = "MIDI Files (*.mid)";
    private static final String ONSAVE_DIALOG_TITLE = "Save as";

    private final SwingFrame frame;
    private final AudioPlayerController controller;

    public SaveMusicAction(SwingFrame frame, AudioPlayerController controller) {
        if (frame == null) throw new IllegalArgumentException("Player frame cannot be null!");
        this.frame = frame;

        if (controller == null) throw new IllegalArgumentException("Audio Player Controller cannot be null!");
        this.controller = controller;
    }

    @Override
    public void run() {
        File fileToSave = SwingUtils.getFileFromChooser(
                SwingUtils.SAVE_FILE_OPERATION,
                frame,
                ONSAVE_FILE_EXTENSION_FILTER,
                ONSAVE_FILTER_DESCRIPTION,
                ONSAVE_DIALOG_TITLE
        );

        if (fileToSave != null) {
            try {
                controller.handleSaveAction(fileToSave);
                SwingMessageUtils.showMessage(frame, "Saved!");
            } catch (MusicExportException ex) {
                SwingMessageUtils.showErrorMessage(frame, ex.getMessage());
            }
        }
    }
}
