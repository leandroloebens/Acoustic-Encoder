package com.acoustic.encoder.features.conversion.view.swing.frame.binder;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.model.MusicParametersState;
import com.acoustic.encoder.features.conversion.model.VoiceParameters;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.VoiceSelectorPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.domain.music.InstrumentOption;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSlider;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingTextArea;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingConversionViewFrameBinder implements SwingConversionViewFrameBinder {

    private final static String EMPTY_TEXT_INPUT_WARNING = "Please enter some text first";

    private final static String INVALID_INSTRUMENT_INPUT_WARNING = "Invalid instrument - Last valid instrument set";

    private static final String ONLOAD_FILE_EXTENSION_FILTER = "txt";
    private static final String ONLOAD_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String ONLOAD_DIALOG_TITLE = "Open";

    private static final String ONSAVE_FILE_EXTENSION_FILTER = "txt";
    private static final String ONSAVE_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String ONSAVE_DIALOG_TITLE = "Save as";

    private final MusicParametersState parameters;

    private boolean bound;

    private final List<Runnable> removers = new ArrayList<>();

    private JRadioButton selectedButton;

    public DefaultSwingConversionViewFrameBinder(MusicParameters parameters) {
        this.parameters = new MusicParametersState(parameters.bpm(), parameters.voiceParameters());
        this.bound = false;
    }

    @Override
    public void bind(ConversionController controller, SwingFrame frame, ConversionViewSwingComponentsWrapper components) {
        if (bound) return;

        ParameterSliderPanel volumePanel = components.volumePanel();
        ParameterSliderPanel octavePanel = components.octavePanel();
        ParameterSliderPanel bpmPanel = components.bpmPanel();
        ParameterComboBoxPanel<InstrumentOption> instrumentPanel = components.instrumentPanel();
        SwingButton converterButton = components.converterButton();
        SwingButton saveButton = components.saveTextButton();
        SwingButton loadButton = components.loadTextButton();
        SwingTextArea textArea = (SwingTextArea) components.scrollPane().getComponent();
        VoiceSelectorPanel voiceSelector = components.voiceSelector();

        setPanelsInitialValues(
                volumePanel,
                octavePanel,
                bpmPanel,
                instrumentPanel
        );

        bindConvertButton(converterButton, frame, instrumentPanel, controller, textArea);
        bindLoadTextButton(loadButton, frame, controller, textArea);
        bindSaveTextButton(saveButton, frame, controller, textArea);

        bindParameterSliderPanel(bpmPanel, () -> parameters.setBpm(bpmPanel.getSlider().getValue()));

        bindParameterSliderPanel(
                volumePanel,
                () -> parameters.setTrackVolume(voiceSelector.getSelectedIndex(), volumePanel.getSlider().getValue())
        );

        bindParameterSliderPanel(
                octavePanel,
                () -> parameters.setTrackOctave(voiceSelector.getSelectedIndex(), octavePanel.getSlider().getValue())
        );

        bindParameterComboBoxPanel(
                instrumentPanel,
                frame,
                () -> parameters.setTrackInstrument(
                        voiceSelector.getSelectedIndex(),
                        instrumentPanel.getSelectedItem().id()
                ),
                INVALID_INSTRUMENT_INPUT_WARNING
        );

        this.selectedButton = voiceSelector.getSelectedButton();
        bindvoiceSelector(voiceSelector, frame, volumePanel, octavePanel, instrumentPanel);

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
            ParameterComboBoxPanel<InstrumentOption> instrumentPanel
    ) {
        VoiceParameters trackZero = parameters.getIndexedTrackParameters(0);

        volumePanel.getSlider().setValue(trackZero.getVolume());
        volumePanel.updateLabel();

        octavePanel.getSlider().setValue(trackZero.getOctave());
        octavePanel.updateLabel();

        bpmPanel.getSlider().setValue(parameters.getBpm());
        bpmPanel.updateLabel();

        instrumentPanel.setSelectedItem(trackZero.getInstrument());
        instrumentPanel.getComboBox().setInitialItem(instrumentPanel.getSelectedItem());
    }

    private boolean validateInstrumentInput(
            SwingFrame frame,
            ParameterComboBoxPanel<InstrumentOption> instrumentPanel
    ) {
        if (instrumentPanel.getComboBox().finishEditing()) {
            JTextField editor = instrumentPanel.getTextEditor();
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
            ParameterComboBoxPanel<InstrumentOption> instrumentPanel,
            ConversionController controller,
            SwingTextArea textArea
    ){
        ActionListener convertListener = event -> {
            try {
                if (textArea.getText().isEmpty()) throw new IllegalArgumentException();
                else if (validateInstrumentInput(frame, instrumentPanel)) {
                    List<VoiceConfig> voices = new ArrayList<>();
                    for (VoiceParameters track : parameters.getAllTracksParameters()) {
                        voices.add(new VoiceConfig(
                                track.getInstrument(),
                                track.getOctave(),
                                track.getVolume())
                        );
                    }

                    controller.handleConvertAction(
                            new UserConversionInput(
                                    textArea.getText(),
                                    parameters.getBpm(),
                                    voices
                            ));
                    System.out.println(parameters);
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

    private void bindParameterSliderPanel(ParameterSliderPanel panel, Runnable action) {
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

    private void bindParameterComboBoxPanel(
            ParameterComboBoxPanel<InstrumentOption> panel,
            SwingFrame frame,
            Runnable action,
            String warningMessage
    ) {
        ActionListener listener = event -> {
            if (frame.isVisible() && panel.getComboBox().finishEditing())
                action.run();
            else
                JOptionPane.showMessageDialog(frame, warningMessage);
        };

        JTextField comboBoxTextEditor = panel.getTextEditor();
        comboBoxTextEditor.addActionListener(listener);
        removers.add(() -> comboBoxTextEditor.removeActionListener(listener));
    }

    private void bindvoiceSelector(
            VoiceSelectorPanel selectorPanel,
            SwingFrame frame,
            ParameterSliderPanel volumePanel,
            ParameterSliderPanel octavePanel,
            ParameterComboBoxPanel<InstrumentOption> instrumentPanel
    ) {
        for (JRadioButton button : selectorPanel.getButtons()) {
            ActionListener listener = event -> {
                selectedButton.setSelected(true);
                button.setSelected(false);

                if (validateInstrumentInput(frame, instrumentPanel)) {
                    selectedButton.setSelected(false);
                    button.setSelected(true);
                    selectedButton = button;

                    VoiceParameters track =
                            parameters.getIndexedTrackParameters(selectorPanel.getButtons().indexOf(button));

                    volumePanel.getSlider().setValue(track.getVolume());
                    octavePanel.getSlider().setValue(track.getOctave());
                    instrumentPanel.setSelectedItem(track.getInstrument());
                    instrumentPanel.getComboBox().finishEditing();
                }
            };

            button.addActionListener(listener);
            removers.add(() -> button.removeActionListener(listener));
        }
    }
}