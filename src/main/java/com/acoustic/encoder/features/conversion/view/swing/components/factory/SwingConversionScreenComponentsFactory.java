package com.acoustic.encoder.features.conversion.view.swing.components.factory;

import com.acoustic.encoder.features.conversion.view.swing.components.config.SwingConversionConfig;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.*;

import javax.swing.*;
import javax.swing.border.Border;
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

        SwingButton conversionButton =
                new SwingButton(config.getString("CONVERTER_BUTTON_TEXT"), null, null);

        SwingButton saveTextButton =
                new SwingButton(config.getString("SAVE_TEXT_BUTTON_TEXT"), null, null);

        SwingButton loadTextButton =
                new SwingButton(config.getString("LOAD_TEXT_BUTTON_TEXT"), null, null);

        SwingTextArea textArea = new SwingTextArea(null, null);
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
        SwingLabel instructionLabel =
                new SwingLabel(config.getString("INSTRUCTION_LABEL_TEXT"), null, instructionLabelBorder);

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

    private ParameterPanel createVolumePanel() {
        SwingSlider volumeSlider = new SwingSlider(
                config.getInt("VOLUME_SLIDER_DIRECTION"),
                config.getInt("VOLUME_SLIDER_MIN"),
                config.getInt("VOLUME_SLIDER_MAX"),
                config.getInt("VOLUME_SLIDER_VALUE"),
                config.getInt("VOLUME_SLIDER_TICK_SPACING"),
                config.getBoolean("VOLUME_SLIDER_SHOW_LABELS")
        );

        String labelText = "Volume: " + volumeSlider.getValue();
        SwingLabel volumeLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, volumeSlider, volumeLabel);
    }

    private ParameterPanel createOctavePanel() {
        SwingSlider octaveSlider = new SwingSlider(
                config.getInt("OCTAVE_SLIDER_DIRECTION"),
                config.getInt("OCTAVE_SLIDER_MIN"),
                config.getInt("OCTAVE_SLIDER_MAX"),
                config.getInt("OCTAVE_SLIDER_VALUE"),
                config.getInt("OCTAVE_SLIDER_TICK_SPACING"),
                config.getBoolean("OCTAVE_SLIDER_SHOW_LABELS")
        );

        String labelText = "Octave: " + octaveSlider.getValue();
        SwingLabel octaveLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, octaveSlider, octaveLabel);
    }

    private ParameterPanel createInstrumentPanel() {
        SwingSlider instrumentSlider = new SwingSlider(
                config.getInt("INSTRUMENT_SLIDER_DIRECTION"),
                config.getInt("INSTRUMENT_SLIDER_MIN"),
                config.getInt("INSTRUMENT_SLIDER_MAX"),
                config.getInt("INSTRUMENT_SLIDER_VALUE"),
                config.getInt("INSTRUMENT_SLIDER_TICK_SPACING"),
                config.getBoolean("INSTRUMENT_SLIDER_SHOW_LABELS")
        );

        String labelText = "Instrument: " + instrumentSlider.getValue();
        SwingLabel instrumentLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, instrumentSlider, instrumentLabel);
    }

    private ParameterPanel createBpmPanel() {
        SwingSlider bpmSlider = new SwingSlider(
                config.getInt("BPM_SLIDER_DIRECTION"),
                config.getInt("BPM_SLIDER_MIN"),
                config.getInt("BPM_SLIDER_MAX"),
                config.getInt("BPM_SLIDER_VALUE"),
                config.getInt("BPM_SLIDER_TICK_SPACING"),
                config.getBoolean("BPM_SLIDER_SHOW_LABELS")
        );

        String labelText = "BPM: " + bpmSlider.getValue();
        SwingLabel bpmLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, bpmSlider, bpmLabel);
    }
}