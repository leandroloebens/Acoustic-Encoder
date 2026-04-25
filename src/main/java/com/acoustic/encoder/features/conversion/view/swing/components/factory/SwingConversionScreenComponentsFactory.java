package com.acoustic.encoder.features.conversion.view.swing.components.factory;

import com.acoustic.encoder.features.conversion.view.swing.components.config.SwingConversionConfig;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.components.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;

public class SwingConversionScreenComponentsFactory implements ConversionScreenComponentsFactory {

    private final static String ILLEGAL_CONFIG_ARGUMENT_MESSAGE = "Illegal config argument!";

    private final SwingConversionConfig config;

    public SwingConversionScreenComponentsFactory(HashMap<String, String> configMap) {
        if (configMap == null) throw new IllegalArgumentException(ILLEGAL_CONFIG_ARGUMENT_MESSAGE);
        this.config = new SwingConversionConfig(configMap);
    }

    @Override
    public ConversionScreenComponentsWrapper createComponents() {

        SwingButton conversionButton = createConverterButton();

        SwingButton saveTextButton = createSaveButton();

        SwingButton loadTextButton = createLoadButton();

        SwingTextArea textArea =
                new SwingTextArea(null, config.getScaledInt("MAIN_SCROLL_TEXTAREA_FONT_SIZE"), null);
        Border scrollPaneBorder = BorderFactory.createEmptyBorder(
                config.getInt("MAIN_SCROLL_TEXTAREA_TGAP"),
                config.getInt("MAIN_SCROLL_TEXTAREA_LGAP"),
                config.getInt("MAIN_SCROLL_TEXTAREA_BGAP"),
                config.getInt("MAIN_SCROLL_TEXTAREA_RGAP")
        );
        SwingVerticalScrollPane scrollPane = new SwingVerticalScrollPane(textArea, scrollPaneBorder);

        Border instructionLabelBorder = BorderFactory.createEmptyBorder(
                config.getInt("INSTRUCTION_LABEL_TGAP"),
                config.getInt("INSTRUCTION_LABEL_LGAP"),
                config.getInt("INSTRUCTION_LABEL_BGAP"),
                config.getInt("INSTRUCTION_LABEL_RGAP")
        );
        SwingLabel instructionLabel = new SwingLabel(
                config.getString("INSTRUCTION_LABEL_TEXT"),
                null,
                config.getScaledInt("INSTRUCTION_LABEL_FONT_SIZE"),
                instructionLabelBorder
        );

        ParameterPanel volumePanel = createVolumePanel();

        ParameterPanel octavePanel = createOctavePanel();

        ParameterPanel instrumentPanel = createInstrumentPanel();

        ParameterPanel bpmPanel = createBpmPanel();

        return new ConversionScreenComponentsWrapper(
                conversionButton,
                saveTextButton,
                loadTextButton,
                scrollPane,
                instructionLabel,
                volumePanel,
                octavePanel,
                instrumentPanel,
                bpmPanel
        );
    }

    private SwingButton createConverterButton() {
        Dimension conversionButtonSize = new Dimension(
                config.getInt("CONVERSION_BUTTON_WIDTH"),
                config.getInt("CONVERSION_BUTTON_HEIGHT")
        );

        SwingButton conversionButton = new SwingButton(
                config.getString("CONVERSION_BUTTON_TEXT"),
                null,
                config.getScaledInt("CONVERSION_BUTTON_FONT_SIZE"),
                null,
                null
        );

        return conversionButton;
    }

    private SwingButton createSaveButton() {
        Dimension saveButtonSize = new Dimension(
                config.getInt("SAVE_TEXT_BUTTON_WIDTH"),
                config.getInt("SAVE_TEXT_BUTTON_HEIGHT")
        );

        SwingButton saveButton = new SwingButton(
                config.getString("SAVE_TEXT_BUTTON_TEXT"),
                null,
                config.getScaledInt("SAVE_TEXT_BUTTON_FONT_SIZE"),
                null,
                null
        );

        return saveButton;
    }

    private SwingButton createLoadButton() {
        Dimension loadButtonSize = new Dimension(
                config.getInt("LOAD_TEXT_BUTTON_WIDTH"),
                config.getInt("LOAD_TEXT_BUTTON_HEIGHT")
        );

        SwingButton loadButton = new SwingButton(
                config.getString("LOAD_TEXT_BUTTON_TEXT"),
                null,
                config.getScaledInt("LOAD_TEXT_BUTTON_FONT_SIZE"),
                null,
                null
        );

        return loadButton;
    }

    private ParameterPanel createVolumePanel() {
        SwingSlider volumeSlider = new SwingSlider(
                config.getInt("VOLUME_SLIDER_DIRECTION"),
                config.getInt("VOLUME_SLIDER_MIN"),
                config.getInt("VOLUME_SLIDER_MIN_TO_SHOW"),
                config.getInt("VOLUME_SLIDER_MAX"),
                config.getInt("VOLUME_SLIDER_MAX_TO_SHOW"),
                config.getInt("VOLUME_SLIDER_VALUE"),
                config.getInt("VOLUME_SLIDER_TICK_SPACING"),
                config.getScaledInt("VOLUME_SLIDER_LABEL_FONT_SIZE")
        );

        SwingLabel volumeLabel = new SwingLabel(config.getScaledInt("VOLUME_LABEL_FONT_SIZE"));

        return new ParameterPanel(volumeSlider, volumeLabel, config.getString("VOLUME_LABEL_TEXT"));
    }

    private ParameterPanel createOctavePanel() {
        SwingSlider octaveSlider = new SwingSlider(
                config.getInt("OCTAVE_SLIDER_DIRECTION"),
                config.getInt("OCTAVE_SLIDER_MIN"),
                config.getInt("OCTAVE_SLIDER_MIN_TO_SHOW"),
                config.getInt("OCTAVE_SLIDER_MAX"),
                config.getInt("OCTAVE_SLIDER_MAX_TO_SHOW"),
                config.getInt("OCTAVE_SLIDER_VALUE"),
                config.getInt("OCTAVE_SLIDER_TICK_SPACING"),
                config.getScaledInt("OCTAVE_SLIDER_LABEL_FONT_SIZE")
        );

        SwingLabel octaveLabel = new SwingLabel(config.getScaledInt("OCTAVE_LABEL_FONT_SIZE"));

        return new ParameterPanel(octaveSlider, octaveLabel, config.getString("OCTAVE_LABEL_TEXT"));
    }

    private ParameterPanel createInstrumentPanel() {
        SwingSlider instrumentSlider = new SwingSlider(
                config.getInt("INSTRUMENT_SLIDER_DIRECTION"),
                config.getInt("INSTRUMENT_SLIDER_MIN"),
                config.getInt("INSTRUMENT_SLIDER_MIN_TO_SHOW"),
                config.getInt("INSTRUMENT_SLIDER_MAX"),
                config.getInt("INSTRUMENT_SLIDER_MAX_TO_SHOW"),
                config.getInt("INSTRUMENT_SLIDER_VALUE"),
                config.getInt("INSTRUMENT_SLIDER_TICK_SPACING"),
                config.getScaledInt("INSTRUMENT_SLIDER_LABEL_FONT_SIZE")
        );

        SwingLabel instrumentLabel = new SwingLabel(config.getScaledInt("INSTRUMENT_LABEL_FONT_SIZE"));

        return new ParameterPanel(instrumentSlider, instrumentLabel, config.getString("INSTRUMENT_LABEL_TEXT"));
    }

    private ParameterPanel createBpmPanel() {
        SwingSlider bpmSlider = new SwingSlider(
                config.getInt("BPM_SLIDER_DIRECTION"),
                config.getInt("BPM_SLIDER_MIN"),
                config.getInt("BPM_SLIDER_MIN_TO_SHOW"),
                config.getInt("BPM_SLIDER_MAX"),
                config.getInt("BPM_SLIDER_MAX_TO_SHOW"),
                config.getInt("BPM_SLIDER_VALUE"),
                config.getInt("BPM_SLIDER_TICK_SPACING"),
                config.getScaledInt("BPM_SLIDER_LABEL_FONT_SIZE")
        );

        SwingLabel bpmLabel = new SwingLabel(config.getScaledInt("BPM_LABEL_FONT_SIZE"));

        return new ParameterPanel(bpmSlider, bpmLabel, config.getString("BPM_LABEL_TEXT"));
    }
}