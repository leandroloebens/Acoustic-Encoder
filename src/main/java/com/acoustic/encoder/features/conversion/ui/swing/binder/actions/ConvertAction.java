package com.acoustic.encoder.features.conversion.ui.swing.binder.actions;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.service.mapper.ConversionParametersService;
import com.acoustic.encoder.features.conversion.ui.swing.binder.provider.TextInputProvider;
import com.acoustic.encoder.features.conversion.ui.swing.binder.validator.InputValidator;
import com.acoustic.encoder.features.conversion.ui.swing.binder.validator.ValidationResult;
import com.acoustic.encoder.features.conversion.ui.swing.synchronizer.SwingConversionViewSynchronizer;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

public class ConvertAction implements Runnable {

    private final static String EMPTY_TEXT_INPUT_WARNING = "Please enter some text first";

    private final SwingFrame frame;
    private final ConversionController controller;
    private final SwingConversionViewSynchronizer synchronizer;
    private final ConversionParametersService parametersService;
    private final TextInputProvider textInputProvider;
    private final InputValidator instrumentValidator;

    public ConvertAction(
            SwingFrame frame,
            ConversionController controller,
            SwingConversionViewSynchronizer synchronizer,
            ConversionParametersService parametersService,
            TextInputProvider textInputProvider,
            InputValidator instrumentValidator
    ) {
        if (frame == null) throw new IllegalArgumentException("Frame cannot be null");
        this.frame = frame;

        if (controller == null) throw new IllegalArgumentException("Controller cannot be null");
        this.controller = controller;

        if (synchronizer == null) throw new IllegalArgumentException("Synchronizer cannot be null");
        this.synchronizer = synchronizer;

        if (parametersService == null) throw new IllegalArgumentException("Parameters service cannot be null");
        this.parametersService = parametersService;

        if (textInputProvider == null) throw new IllegalArgumentException("Text input provider cannot be null");
        this.textInputProvider = textInputProvider;

        if (instrumentValidator == null) throw new IllegalArgumentException("Instrument validator cannot be null");
        this.instrumentValidator = instrumentValidator;
    }

    @Override
    public void run() {
        try {
            String cleanedText = textInputProvider.getTextInput().stripTrailing();

            if (!cleanedText.trim().isEmpty()) {
                ValidationResult result = instrumentValidator.validate();
                if (result.valid()) {
                    controller.handleConvertAction(parametersService.wrapMusicProject(
                            cleanedText, synchronizer.getParameters())
                    );
                    System.out.println(synchronizer.getParameters().toString());
                }
                else SwingUtils.showWarningMessage(frame, result.feedbackMessage());
            }
            else SwingUtils.showWarningMessage(frame, EMPTY_TEXT_INPUT_WARNING);
        } catch (IllegalStateException e) {
            SwingUtils.showErrorMessage(frame, e.getMessage());
        }
    }
}
