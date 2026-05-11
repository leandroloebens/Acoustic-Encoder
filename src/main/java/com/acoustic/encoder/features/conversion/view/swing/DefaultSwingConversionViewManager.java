package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.model.TrackParameters;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.event.ConversionScreenClosedEvent;
import com.acoustic.encoder.features.conversion.view.ConversionViewManager;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DefaultSwingConversionViewManager implements ConversionViewManager {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int WINDOW_MIN_HEIGHT = 650;
    private final static int WINDOW_MIN_WIDTH = 850;
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;
    private final static int INITIAL_TRACK_INDEX = 0;

    private final SwingConversionViewAssembler assembler;

    private final EventBus eventBus;

    private SwingFrame frame;

    private int defaultBpm;
    private List<TrackParameters> defaultTrackParameters;
    private int currentTrack;

    public DefaultSwingConversionViewManager(
            SwingConversionViewAssembler assembler,
            MusicParameters defaultTrackParameters,
            EventBus eventBus
    ) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

        if (defaultTrackParameters == null) throw new IllegalArgumentException("Default parameters cannot be null!");
        this.defaultBpm = defaultTrackParameters.bpm();
        this.defaultTrackParameters = defaultTrackParameters.trackParameters();

        this.currentTrack = INITIAL_TRACK_INDEX;

    }

    @Override
    public void startFrame(ConversionController conversionController) {
        Dimension windowInitialSize =
            new Dimension(
                (int)(WINDOW_MIN_WIDTH * SwingUtils.getScreenScaleRatio()),
                (int)(WINDOW_MIN_HEIGHT * SwingUtils.getScreenScaleRatio())
            );

        System.out.println("Starting conversion screen with initial size: " + windowInitialSize);

        frame = assembler.assembleFrame(
            WINDOW_TITLE,
            windowInitialSize,
            FRAME_EXIT_OPERATION,
            new MusicParameters(defaultBpm, defaultTrackParameters),
            new ActionHandler(conversionController)
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new ConversionScreenClosedEvent());
            }
        });

    }

    @Override
    public void showFrame() { frame.setVisible(true); }

    @Override
    public void hideFrame() { frame.setVisible(false); }

    @Override
    public void disposeFrame() { frame.dispose(); }

    private class ActionHandler implements SwingConversionViewActionHandler {
        private static final String ONLOAD_FILE_EXTENSION_FILTER = "txt";
        private static final String ONLOAD_FILTER_DESCRIPTION = "Text Files (*.txt)";
        private static final String ONLOAD_DIALOG_TITLE = "Open";

        private static final String ONSAVE_FILE_EXTENSION_FILTER = "txt";
        private static final String ONSAVE_FILTER_DESCRIPTION = "Text Files (*.txt)";
        private static final String ONSAVE_DIALOG_TITLE = "Save as";

        private final ConversionController controller;

        public ActionHandler(ConversionController controller) {
            this.controller = controller;
        }

        @Override
        public void onConvert() {
            TrackParameters trackZero = defaultTrackParameters.getFirst();

            controller.handleConvertAction(
                    new UserConversionInput(
                        assembler.getInputText(),
                        trackZero.getInstrument(),
                        defaultBpm,
                        trackZero.getOctave(),
                        trackZero.getVolume()
                    ));

            hideFrame();
        }

        @Override
        public void onLoad() {
            File fileToLoad = SwingUtils.getFileFromChooser(
                    SwingUtils.LOAD_FILE_OPERATION,
                    frame,
                    ONLOAD_FILE_EXTENSION_FILTER,
                    ONLOAD_FILTER_DESCRIPTION,
                    ONLOAD_DIALOG_TITLE
            );

            if (fileToLoad != null) {

                try {
                    String text = controller.handleLoadTextAction(fileToLoad);
                    assembler.setInputText(text);
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Error loading file: " + ex.getMessage(),
                            "Load Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        }

        @Override
        public void onSave() {
            File fileToSave = SwingUtils.getFileFromChooser(
                    SwingUtils.SAVE_FILE_OPERATION,
                    frame,
                    ONSAVE_FILE_EXTENSION_FILTER,
                    ONSAVE_FILTER_DESCRIPTION,
                    ONSAVE_DIALOG_TITLE
            );

            if (fileToSave != null) {

                try {
                    controller.handleSaveTextAction(assembler.getInputText(), fileToSave);
                    JOptionPane.showMessageDialog(frame, "Saved!");
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
                }

            }
        }

        @Override
        public void onVolumeChange() {
            defaultTrackParameters.getFirst().setVolume(assembler.getVolumeValue());
        }

        @Override
        public void onOctaveChange() {
            defaultTrackParameters.getFirst().setOctave(assembler.getOctaveValue());
        }

        @Override
        public void onBpmChange() {
            defaultBpm = assembler.getBpmValue();
        }

        @Override
        public void onInstrumentChange() {
            defaultTrackParameters.getFirst().setInstrument(assembler.getInstrumentValue());
            System.out.println(assembler.getInstrumentValue());
        }
    }

}