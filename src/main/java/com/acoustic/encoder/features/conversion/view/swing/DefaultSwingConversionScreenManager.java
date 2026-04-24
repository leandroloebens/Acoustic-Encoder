package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.event.ConversionClosedEvent;
import com.acoustic.encoder.features.conversion.view.ConversionScreenManager;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.model.MusicConfig;
import com.acoustic.encoder.shared.view.swing.SwingFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class DefaultSwingConversionScreenManager implements ConversionScreenManager {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int WINDOW_HEIGHT = 400;
    private final static int WINDOW_WIDTH = 500;
    private final static int FRAME_EXIT_OPERATION = JFrame.DISPOSE_ON_CLOSE;

    private final SwingConversionScreenAssembler assembler;

    private final EventBus eventBus;

    private SwingFrame frame;

    private int defaultVolume;
    private int defaultOctave;
    private int defaultInstrument;
    private int defaultBpm;

    public DefaultSwingConversionScreenManager(SwingConversionScreenAssembler assembler, EventBus eventBus) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (eventBus == null) throw new IllegalArgumentException("EventBus cannot be null!");
        this.eventBus = eventBus;

    }

    @Override
    public void startFrame(ConversionController conversionController) {
        frame = assembler.assembleFrame(
            WINDOW_TITLE,
            WINDOW_WIDTH,
            WINDOW_HEIGHT,
            FRAME_EXIT_OPERATION,
            new EventHandler(conversionController)
        );

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new ConversionClosedEvent());
            }
        });

        showFrame();
    }

    @Override
    public void showFrame() { frame.setVisible(true); }

    @Override
    public void hideFrame() { frame.setVisible(false); }

    @Override
    public void disposeFrame() { frame.dispose(); }

    @Override
    public void setInitialDefaultParameters(MusicConfig parameters) {
        this.defaultVolume = parameters.defaultVolume();
        this.defaultOctave = parameters.defaultOctave();
        this.defaultInstrument = parameters.defaultMidiInstrument();
        this.defaultBpm = parameters.bpm();
    }

    private class EventHandler implements SwingEventHandler {
        private final ConversionController controller;

        public EventHandler(ConversionController controller) {
            this.controller = controller;
        }

        @Override
        public void onConvert() {
            controller.handleConvertAction(
                    new UserConversionInput(
                        assembler.getInputText(),
                        defaultInstrument,
                        defaultBpm,
                        defaultOctave,
                        defaultVolume
                    ));

            hideFrame();
        }

        @Override
        public void onLoad() {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Open");

            int userSelection = fileChooser.showOpenDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();

                try {
                    String text = controller.handleLoadTextAction(fileToLoad);
                    assembler.setInputText(text);
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading file: " + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        @Override
        public void onSave() {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Save as");

            int userSelection = fileChooser.showSaveDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();

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
            defaultVolume = assembler.getVolumeSliderValue();
        }

        @Override
        public void onOctaveChange() {
            defaultOctave = assembler.getOctaveSliderValue();
        }

        @Override
        public void onBpmChange() {
            defaultBpm = assembler.getBpmSliderValue();
        }

        @Override
        public void onInstrumentChange() {
            defaultInstrument = assembler.getInstrumentSliderValue();
        }
    }

}