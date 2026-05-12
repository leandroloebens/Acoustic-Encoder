package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.model.MusicParametersState;
import com.acoustic.encoder.features.conversion.model.TrackParameters;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.*;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingConversionViewBinder implements SwingConversionViewBinder {

    private final static String EMPTY_TEXT_INPUT_WARNING = "Please enter some text first";

    private final static String INVALID_INSTRUMENT_INPUT_WARNING = "Invalid instrument - Last valid instrument set";

    private static final String ONLOAD_FILE_EXTENSION_FILTER = "txt";
    private static final String ONLOAD_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String ONLOAD_DIALOG_TITLE = "Open";

    private static final String ONSAVE_FILE_EXTENSION_FILTER = "txt";
    private static final String ONSAVE_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String ONSAVE_DIALOG_TITLE = "Save as";

    private MusicParametersState parameters;

    private boolean bound;
    private final List<Runnable> removers = new ArrayList<>();

    public DefaultSwingConversionViewBinder(MusicParameters parameters) {
        this.parameters = new MusicParametersState(parameters.bpm(), parameters.trackParameters());
        this.bound = false;
    }

    @Override
    public void bind(ConversionController controller, SwingFrame frame, ConversionViewComponentsWrapper components) {
        if (bound) return;

        ParameterSliderPanel volumePanel = components.volumePanel();
        ParameterSliderPanel octavePanel = components.octavePanel();
        ParameterSliderPanel bpmPanel = components.bpmPanel();
        ParameterComboBoxPanel<Integer> instrumentPanel = components.instrumentPanel();
        SwingButton converterButton = components.converterButton();
        SwingButton saveButton = components.saveTextButton();
        SwingButton loadButton = components.loadTextButton();
        SwingTextArea textArea = (SwingTextArea) components.scrollPane().getComponent();

        setPanelsInitialValues(
                volumePanel,
                octavePanel,
                bpmPanel,
                instrumentPanel
        );

        bindConvertButton(converterButton, frame, instrumentPanel, controller, textArea);
        bindLoadTextButton(loadButton, frame, controller, textArea);
        bindSaveTextButton(saveButton, frame, controller, textArea);

        bindParameterPanel(bpmPanel, () -> parameters.setBpm(bpmPanel.getSlider().getValue()));
        bindParameterPanel(volumePanel, () -> parameters.setTrackVolume(0, volumePanel.getSlider().getValue()));
        bindParameterPanel(octavePanel, () -> parameters.setTrackOctave(0, octavePanel.getSlider().getValue()));

        bindInstrumentPanel(instrumentPanel, frame);

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

    private void setPanelsInitialValues(
            ParameterSliderPanel volumePanel,
            ParameterSliderPanel octavePanel,
            ParameterSliderPanel bpmPanel,
            ParameterComboBoxPanel<Integer> instrumentPanel
    ) {
        TrackParameters trackZero = parameters.getIndexedTrackParameters(0);

        volumePanel.getSlider().setValue(trackZero.getVolume());
        volumePanel.updateLabel();

        octavePanel.getSlider().setValue(trackZero.getOctave());
        octavePanel.updateLabel();

        bpmPanel.getSlider().setValue(parameters.getBpm());
        bpmPanel.updateLabel();

        instrumentPanel.getComboBox().setSelectedOriginalIndex(trackZero.getInstrument());
        instrumentPanel.getComboBox().setInitialItem(
                (Integer) instrumentPanel.getComboBox().getSelectedItem()
        );
    }

    private boolean validateInstrumentInput(SwingFrame frame, ParameterComboBoxPanel<Integer> instrumentPanel) {
        if (instrumentPanel.getComboBox().finishEditing()) {
            JTextField editor = (JTextField) instrumentPanel.getComboBox().getEditor().getEditorComponent();
            editor.postActionEvent(); // Manually fires the event to update the instrument value

            return true;
        }
        else {
            JOptionPane.showMessageDialog(frame, INVALID_INSTRUMENT_INPUT_WARNING);
            return false;
        }
    }

    private void bindConvertButton(
            SwingButton converterButton,
            SwingFrame frame,
            ParameterComboBoxPanel<Integer> instrumentPanel,
            ConversionController controller,
            SwingTextArea textArea
    ){
        ActionListener convertListener = event -> {
            try {
                if (textArea.getText().isEmpty()) throw new IllegalArgumentException();
                else if (validateInstrumentInput(frame, instrumentPanel)) {
                    controller.handleConvertAction(
                            new UserConversionInput(
                                    textArea.getText(),
                                    parameters.getIndexedTrackParameters(0).getInstrument(),
                                    parameters.getBpm(),
                                    parameters.getIndexedTrackParameters(0).getOctave(),
                                    parameters.getIndexedTrackParameters(0).getVolume()
                            ));

                    frame.setVisible(false);
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, EMPTY_TEXT_INPUT_WARNING);
            }
        };

        converterButton.addActionListener(convertListener);
        removers.add(() -> converterButton.removeActionListener(convertListener));
    }

    private void bindLoadTextButton(
            SwingButton loadButton,
            SwingFrame frame,
            ConversionController controller,
            SwingTextArea textArea
    ) {
        ActionListener loadTextListener = event -> {
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
                    textArea.setText(text);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Error loading file: " + ex.getMessage(),
                            "Load Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        loadButton.addActionListener(loadTextListener);
        removers.add(() -> loadButton.removeActionListener(loadTextListener));
    }

    private void bindSaveTextButton(
            SwingButton saveButton,
            SwingFrame frame,
            ConversionController controller,
            SwingTextArea textArea
    ){
        ActionListener saveTextListener = event -> {
            File fileToSave = SwingUtils.getFileFromChooser(
                    SwingUtils.SAVE_FILE_OPERATION,
                    frame,
                    ONSAVE_FILE_EXTENSION_FILTER,
                    ONSAVE_FILTER_DESCRIPTION,
                    ONSAVE_DIALOG_TITLE
            );

            if (fileToSave != null) {

                try {
                    controller.handleSaveTextAction(textArea.getText(), fileToSave);
                    JOptionPane.showMessageDialog(frame, "Saved!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
                }

            }
        };

        saveButton.addActionListener(saveTextListener);
        removers.add(() -> saveButton.removeActionListener(saveTextListener));
    }

    private void bindParameterPanel(ParameterSliderPanel panel, Runnable action) {
        ChangeListener listener = event -> {
            SwingSlider slider = panel.getSlider();
            int value = slider.getValue();

            if (value < slider.getMinToShow()) slider.setValue(slider.getMinToShow());
            else if (value > slider.getMaxToShow()) slider.setValue(slider.getMaxToShow());

            panel.updateLabel();
            action.run();
        };

        panel.getSlider().addChangeListener(listener);
        removers.add(() -> panel.getSlider().removeChangeListener(listener));
    }

    private void bindInstrumentPanel(ParameterComboBoxPanel<Integer> panel, SwingFrame frame) {
        ActionListener listener = event -> {
            if (frame.isVisible() && panel.getComboBox().finishEditing())
                parameters.setTrackInstrument(0, panel.getComboBox().getSelectedOriginalIndex());
            else
                JOptionPane.showMessageDialog(frame, INVALID_INSTRUMENT_INPUT_WARNING);
        };

        JTextField instrumentTextEditor = (JTextField) panel.getComboBox().getEditor().getEditorComponent();
        instrumentTextEditor.addActionListener(listener);
        removers.add(() -> instrumentTextEditor.removeActionListener(listener));
    }
}