package com.acoustic.encoder.features.conversion.view.swing.binder;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.shared.dto.MusicProject;
import com.acoustic.encoder.features.conversion.model.MusicParametersState;
import com.acoustic.encoder.features.conversion.model.VoiceParametersState;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.dto.InstrumentOption;
import com.acoustic.encoder.shared.event.EventBus;
import com.acoustic.encoder.shared.event.EventListener;
import com.acoustic.encoder.shared.event.ProjectReadyToOpen;
import com.acoustic.encoder.shared.model.VoiceConfig;
import com.acoustic.encoder.shared.view.swing.components.*;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingConversionViewEventBinder implements SwingConversionViewEventBinder {

    private static final String NULL_EVENT_BUS_ERROR_MSG = "EventBus cannot be null!";
    private static final String NULL_PARAMETERS_SERVICE_ERROR_MSG = "Parameters service cannot be null!";
    private final static String EMPTY_TEXT_INPUT_WARNING = "Please enter some text first";
    private final static String INVALID_INSTRUMENT_INPUT_WARNING = "Invalid instrument - Last valid instrument set";

    private static final String LOAD_TEXT_FILE_EXTENSION_FILTER = "txt";
    private static final String LOAD_TEXT_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String LOAD_TEXT_DIALOG_TITLE = "Open";

    private static final String SAVE_TEXT_FILE_EXTENSION_FILTER = "txt";
    private static final String SAVE_TEXT_FILTER_DESCRIPTION = "Text Files (*.txt)";
    private static final String SAVE_TEXT_DIALOG_TITLE = "Save as";

    private static final String SAVE_PROJECT_FILE_EXTENSION_FILTER = "aef";
    private static final String SAVE_PROJECT_FILTER_DESCRIPTION = "Acoustic Encoder Format (*.aef)";
    private static final String SAVE_PROJECT_DIALOG_TITLE = "Save as";

    private final EventBus eventBus;

    private final ConversionParametersService parametersService;

    private MusicParametersState parameters = new MusicParametersState();

    private boolean bound = false;

    private ConversionViewSwingComponentsWrapper comps;

    private final List<Runnable> removers = new ArrayList<>();

    private JRadioButton selectedButton;

    public DefaultSwingConversionViewEventBinder(EventBus eventBus, ConversionParametersService parametersService) {
        if (eventBus == null) throw new IllegalArgumentException(NULL_EVENT_BUS_ERROR_MSG);
        this.eventBus = eventBus;

        if (parametersService == null) throw new IllegalArgumentException(NULL_PARAMETERS_SERVICE_ERROR_MSG);
        this.parametersService = parametersService;
    }

    @Override
    public void bind(
            ConversionController controller,
            SwingFrame frame,
            ConversionViewSwingComponentsWrapper components
    ) {
        if (bound) return;

        this.comps = components;

//        setInitialVoicesValues();

        bindConvertButton(frame, controller);
        bindLoadTextButton(frame, controller);
        bindSaveTextButton(frame, controller);
        bindSaveProjectButton(frame, controller);

        bindParameterSliderPanel(
                components.bpmPanel(),
                () -> parameters.setBpm(comps.bpmPanel().getSlider().getValue()));

        bindParameterSliderPanel(
                comps.volumePanel(),
                () -> parameters.setVoiceVolume(
                        comps.voiceSelector().getSelectedIndex(),
                        comps.volumePanel().getSlider().getValue()
                )
        );

        bindParameterSliderPanel(
                comps.octavePanel(),
                () -> parameters.setVoiceOctave(comps.voiceSelector().getSelectedIndex(), comps.octavePanel().getSlider().getValue())
        );

        bindParameterComboBoxPanel(
                comps.instrumentPanel(),
                frame,
                () -> parameters.setVoiceInstrument(
                        comps.voiceSelector().getSelectedIndex(),
                        comps.instrumentPanel().getSelectedItem().id()
                ),
                INVALID_INSTRUMENT_INPUT_WARNING
        );

        this.selectedButton = comps.voiceSelector().getSelectedButton();
        bindVoiceSelector(frame);

        EventListener<ProjectReadyToOpen> openListener =
                event -> updateInitialParameters(event.project());
        eventBus.subscribe(ProjectReadyToOpen.class, openListener);
//        removers.add(() -> eventBus.unsubscribe(ProjectReadyToOpen.class, openListener));

        bound = true;
    }

    @Override
    public void unbind() {
        if (!bound) return;

        for (Runnable remove : removers) {
            remove.run();
        }

        removers.clear();
        comps = null;

        bound = false;
    }

    private void updateInitialParameters(MusicProject initialProject) {
        if (initialProject == null) return;

        this.parameters = parametersService.unwrapMusicProject(initialProject);

        setInitialVoicesValues();

        SwingTextArea textArea = (SwingTextArea) comps.scrollPane().getComponent();
        textArea.setText(initialProject.text());
    }

    private void setInitialVoicesValues() {
        VoiceParametersState trackZero = parameters.getIndexedVoice(0);

        comps.volumePanel().getSlider().setValue(trackZero.getVolume());
        comps.volumePanel().updateLabel();

        comps.octavePanel().getSlider().setValue(trackZero.getOctave());
        comps.octavePanel().updateLabel();

        comps.bpmPanel().getSlider().setValue(parameters.getBpm());
        comps.bpmPanel().updateLabel();

        comps.instrumentPanel().setSelectedItem(trackZero.getInstrument());
        comps.instrumentPanel().getComboBox().setInitialItem(comps.instrumentPanel().getSelectedItem());
    }

    private boolean validateInstrumentInput(SwingFrame frame) {
        if (comps.instrumentPanel().getComboBox().finishEditing()) {
            JTextField editor = comps.instrumentPanel().getTextEditor();
            editor.postActionEvent(); // Manually fires the event to update the instrument value
            return true;
        }
        else {
            JOptionPane.showMessageDialog(frame, INVALID_INSTRUMENT_INPUT_WARNING);
            return false;
        }
    }

    private void bindConvertButton(SwingFrame frame, ConversionController controller){
        ActionListener convertListener = event -> {
            try {
                SwingTextArea textArea = (SwingTextArea) comps.scrollPane().getComponent();
                if (textArea.getText().isEmpty()) throw new IllegalArgumentException();
                else if (validateInstrumentInput(frame)) {
                    controller.handleConvertAction(parametersService.wrapMusicProject(textArea.getText(), parameters));
                    System.out.println(parameters);
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, EMPTY_TEXT_INPUT_WARNING);
            }
        };

        comps.converterButton().addActionListener(convertListener);
        removers.add(() ->comps.converterButton().removeActionListener(convertListener));
    }

    private void bindLoadTextButton(SwingFrame frame, ConversionController controller) {
        ActionListener loadTextListener = event -> {
            File fileToLoad = SwingUtils.getFileFromChooser(
                    SwingUtils.LOAD_FILE_OPERATION,
                    frame,
                    LOAD_TEXT_FILE_EXTENSION_FILTER,
                    LOAD_TEXT_FILTER_DESCRIPTION,
                    LOAD_TEXT_DIALOG_TITLE
            );

            if (fileToLoad != null) {
                try {
                    SwingTextArea textArea = (SwingTextArea) comps.scrollPane().getComponent();
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

        comps.loadTextButton().addActionListener(loadTextListener);
        removers.add(() -> comps.loadTextButton().removeActionListener(loadTextListener));
    }

    private void bindSaveTextButton(SwingFrame frame, ConversionController controller){
        ActionListener saveTextListener = event -> {
            File fileToSave = SwingUtils.getFileFromChooser(
                    SwingUtils.SAVE_FILE_OPERATION,
                    frame,
                    SAVE_TEXT_FILE_EXTENSION_FILTER,
                    SAVE_TEXT_FILTER_DESCRIPTION,
                    SAVE_TEXT_DIALOG_TITLE
            );

            if (fileToSave != null) {
                try {
                    SwingTextArea textArea = (SwingTextArea) comps.scrollPane().getComponent();
                    controller.handleSaveTextAction(textArea.getText(), fileToSave);
                    JOptionPane.showMessageDialog(frame, "Saved!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
                }
            }
        };

        comps.saveTextButton().addActionListener(saveTextListener);
        removers.add(() -> comps.saveTextButton().removeActionListener(saveTextListener));
    }

    private void bindSaveProjectButton(SwingFrame frame, ConversionController controller) {
        ActionListener saveProjectListener = event -> {
            File fileToSave = SwingUtils.getFileFromChooser(
                    SwingUtils.SAVE_FILE_OPERATION,
                    frame,
                    SAVE_PROJECT_FILE_EXTENSION_FILTER,
                    SAVE_PROJECT_FILTER_DESCRIPTION,
                    SAVE_PROJECT_DIALOG_TITLE
            );

            if (fileToSave != null) {
                try {
                    SwingTextArea textArea = (SwingTextArea) comps.scrollPane().getComponent();
                    MusicProject project = parametersService.wrapMusicProject(textArea.getText(), parameters);
                    controller.handleSaveProjectAction(project, fileToSave);
                    JOptionPane.showMessageDialog(frame, "Saved!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
                }
            }
        };

        comps.saveProjectButton().addActionListener(saveProjectListener);
        removers.add(() -> comps.saveProjectButton().removeActionListener(saveProjectListener));
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

    private void bindVoiceSelector(SwingFrame frame) {
        for (JRadioButton button : comps.voiceSelector().getButtons()) {
            ActionListener listener = event -> {
                selectedButton.setSelected(true);
                button.setSelected(false);

                if (validateInstrumentInput(frame)) {
                    selectedButton.setSelected(false);
                    button.setSelected(true);
                    selectedButton = button;

                    VoiceParametersState track =
                            parameters.getIndexedVoice(comps.voiceSelector().getButtons().indexOf(button));

                    comps.volumePanel().getSlider().setValue(track.getVolume());
                    comps.octavePanel().getSlider().setValue(track.getOctave());
                    comps.instrumentPanel().setSelectedItem(track.getInstrument());
                    comps.instrumentPanel().getComboBox().finishEditing();
                }
            };

            button.addActionListener(listener);
            removers.add(() -> button.removeActionListener(listener));
        }
    }
}