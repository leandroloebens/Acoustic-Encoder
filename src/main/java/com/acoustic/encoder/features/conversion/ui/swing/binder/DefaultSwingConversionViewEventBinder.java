package com.acoustic.encoder.features.conversion.ui.swing.binder;

import com.acoustic.encoder.domain.event.EventBus;
import com.acoustic.encoder.domain.event.EventListener;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.features.conversion.event.ConversionScreenCloseRequestEvent;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizerFactory;
import com.acoustic.encoder.features.start.event.ProjectReadyToOpen;
import com.acoustic.encoder.domain.music.InstrumentOption;
import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSlider;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSwingConversionViewEventBinder implements SwingConversionViewEventBinder {

    private static final String NULL_EVENT_BUS_ERROR_MSG = "EventBus cannot be null!";
    private static final String NULL_PARAMETERS_SERVICE_ERROR_MSG = "Parameters service cannot be null!";
    private static final String NULL_SYNCHRONIZER_FACTORY_ERROR_MSG = "Synchronizer factory cannot be null!";

    private final static String EMPTY_TEXT_INPUT_WARNING = "Please enter some text first";
    private final static String INVALID_INSTRUMENT_INPUT_WARNING = "Invalid instrument - Last valid instrument set";

    private static final String NULL_COMPONENTS_ERROR_MSG = "Components cannot be null!";

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

    private final SwingConversionViewSynchronizerFactory synchronizerFactory;

    private SwingConversionViewSynchronizer synchronizer;

    private boolean bound = false;

    private ConversionViewSwingComponentsWrapper comps;

    private final List<Runnable> removers = new ArrayList<>();

    private JRadioButton previousButton;

    public DefaultSwingConversionViewEventBinder(
            EventBus eventBus,
            ConversionParametersService parametersService,
            SwingConversionViewSynchronizerFactory synchronizerFactory
    ) {
        if (eventBus == null) throw new IllegalArgumentException(NULL_EVENT_BUS_ERROR_MSG);
        this.eventBus = eventBus;

        if (parametersService == null) throw new IllegalArgumentException(NULL_PARAMETERS_SERVICE_ERROR_MSG);
        this.parametersService = parametersService;

        if (synchronizerFactory == null) throw new IllegalArgumentException(NULL_SYNCHRONIZER_FACTORY_ERROR_MSG);
        this.synchronizerFactory = synchronizerFactory;
    }

    @Override
    public SwingConversionViewSynchronizer bind(
            ConversionController controller,
            SwingFrame frame,
            ConversionViewSwingComponentsWrapper components
    ) {
        if (bound) return this.synchronizer;

        if (components == null) throw new IllegalArgumentException(NULL_COMPONENTS_ERROR_MSG);
        this.comps = components;

        this.synchronizer = synchronizerFactory.createSynchronizer(comps, parametersService);

        bindConvertButton(frame, controller);
        bindLoadTextButton(frame, controller);
        bindSaveTextButton(frame, controller);
        bindLoadProjectButton(frame, controller);
        bindSaveProjectButton(frame, controller);

        bindParameterSliderPanel(components.bpmPanel(), () -> synchronizer.syncBpm());

        bindParameterSliderPanel(comps.volumePanel(), () -> synchronizer.syncVoiceVolume());

        bindParameterSliderPanel(comps.octavePanel(), () -> synchronizer.syncVoiceOctave());

        bindParameterComboBoxPanel(
                comps.instrumentPanel(),
                frame,
                () -> synchronizer.syncVoiceInstrument(),
                INVALID_INSTRUMENT_INPUT_WARNING
        );

        this.previousButton = comps.voiceSelector().getSelectedButton();
        bindVoiceSelector(frame);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventBus.publish(new ConversionScreenCloseRequestEvent());
            }
        });

        EventListener<ProjectReadyToOpen> openListener =
                event -> synchronizer.syncMusicProject(event.project());
        eventBus.subscribe(ProjectReadyToOpen.class, openListener);
        removers.add(() -> eventBus.unsubscribe(ProjectReadyToOpen.class, openListener));

        bound = true;

        return this.synchronizer;
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

    private boolean validateInstrumentInput(SwingFrame frame) {
        if (comps.instrumentPanel().isEditorInputValid()) {
            JTextField editor = comps.instrumentPanel().getTextEditor();
            editor.postActionEvent(); // Manually fires the event to update the instrument value
            return true;
        }
        else {
            SwingUtils.showErrorMessage(frame, INVALID_INSTRUMENT_INPUT_WARNING);
            return false;
        }
    }

    private void bindConvertButton(SwingFrame frame, ConversionController controller){
        ActionListener convertListener = event -> {
            try {
                if (comps.mainTextAreaPanel().isTextEmpty()) throw new IllegalArgumentException();
                else if (validateInstrumentInput(frame)) {
                    controller.handleConvertAction(parametersService.wrapMusicProject(
                            comps.mainTextAreaPanel().getText(), synchronizer.getParameters()));
                    System.out.println(synchronizer.getParameters().toString());
                }
            } catch (IllegalArgumentException e) {
                SwingUtils.showWarningMessage(frame, EMPTY_TEXT_INPUT_WARNING);
            } catch (IllegalStateException e) {
                SwingUtils.showErrorMessage(frame, e.getMessage());
            }
        };

        comps.converterButton().addActionListener(convertListener);
        removers.add(() -> comps.converterButton().removeActionListener(convertListener));
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
                    String text = controller.handleLoadTextAction(fileToLoad);
                    comps.mainTextAreaPanel().setText(text);
                } catch (IOException ex) {
                    SwingUtils.showErrorMessage(frame, "Error loading file: " + ex.getMessage());
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
                    controller.handleSaveTextAction(comps.mainTextAreaPanel().getText(), fileToSave);
                    SwingUtils.showMessage(frame, "Saved!");
                } catch (IOException ex) {
                    SwingUtils.showErrorMessage(frame, "Error saving file: " + ex.getMessage());
                }
            }
        };

        comps.saveTextButton().addActionListener(saveTextListener);
        removers.add(() -> comps.saveTextButton().removeActionListener(saveTextListener));
    }

    private void bindLoadProjectButton(SwingFrame frame, ConversionController controller) {
        ActionListener loadProjectListener = event -> {
            File fileToLoad = SwingUtils.getFileFromChooser(
                    SwingUtils.LOAD_FILE_OPERATION,
                    frame,
                    SAVE_PROJECT_FILE_EXTENSION_FILTER,
                    SAVE_PROJECT_FILTER_DESCRIPTION,
                    SAVE_PROJECT_DIALOG_TITLE
            );

            if (fileToLoad != null) {
                try {
                    MusicProject loadedProject = controller.handleLoadProjectAction(fileToLoad);
                    synchronizer.syncMusicProject(loadedProject);
                } catch (IOException e) {
                    SwingUtils.showErrorMessage(frame, "Error loading project: " + e.getMessage());
                }
            }
        };

        comps.loadProjectButton().addActionListener(loadProjectListener);
        removers.add(() -> comps.loadProjectButton().removeActionListener(loadProjectListener));
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
                    MusicProject project = parametersService.wrapMusicProject(
                            comps.mainTextAreaPanel().getText(), synchronizer.getParameters());
                    controller.handleSaveProjectAction(project, fileToSave);
                    SwingUtils.showMessage(frame, "Saved!");
                } catch (IOException ex) {
                    SwingUtils.showErrorMessage(frame, "Error saving project file: " + ex.getMessage());
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
                SwingUtils.showWarningMessage(frame, warningMessage);
        };

        JTextField comboBoxTextEditor = panel.getTextEditor();
        comboBoxTextEditor.addActionListener(listener);
        removers.add(() -> comboBoxTextEditor.removeActionListener(listener));
    }

    private void bindVoiceSelector(SwingFrame frame) {
        for (JRadioButton button : comps.voiceSelector().getButtons()) {
            ActionListener listener = event -> {
                previousButton.setSelected(true);
                button.setSelected(false);

                if (validateInstrumentInput(frame)) {
                    previousButton.setSelected(false);
                    button.setSelected(true);
                    previousButton = button;

                    synchronizer.syncVoiceSelector();
                }
            };

            button.addActionListener(listener);
            removers.add(() -> button.removeActionListener(listener));
        }
    }
}