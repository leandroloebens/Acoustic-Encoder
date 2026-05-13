package com.acoustic.encoder.features.conversion.view.swing.components.factory;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.TrackSelectorPanel;
import com.acoustic.encoder.shared.view.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.shared.view.swing.components.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultSwingConversionViewComponentsFactory implements SwingConversionViewComponentsFactory {

    private final static String ILLEGAL_CONFIG_ARGUMENT_MESSAGE = "Illegal config argument!";

    private final static int SCROLL_PANE_MAX_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.6);

    private final SwingViewConfigWrapper config;

    public DefaultSwingConversionViewComponentsFactory(HashMap<String, String> configMap) {
        if (configMap == null) throw new IllegalArgumentException(ILLEGAL_CONFIG_ARGUMENT_MESSAGE);
        this.config = new SwingViewConfigWrapper(configMap);
    }

    @Override
    public ConversionViewComponentsWrapper createComponents() {

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

        SwingVerticalScrollPane scrollPane = new SwingVerticalScrollPane(
                textArea,
                scrollPaneBorder,
                new Dimension(SCROLL_PANE_MAX_WIDTH, Integer.MAX_VALUE)
        );

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

        TrackSelectorPanel trackSelector = createTrackSelector();

        ParameterSliderPanel volumePanel = createVolumePanel();

        ParameterSliderPanel octavePanel = createOctavePanel();

        ParameterComboBoxPanel<String> instrumentPanel = createInstrumentPanel();

        ParameterSliderPanel bpmPanel = createBpmPanel();

        return new ConversionViewComponentsWrapper(
                conversionButton,
                saveTextButton,
                loadTextButton,
                scrollPane,
                instructionLabel,
                trackSelector,
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

        return new SwingButton(
                config.getString("CONVERSION_BUTTON_TEXT"),
                null,
                config.getScaledInt("CONVERSION_BUTTON_FONT_SIZE"),
                null,
                null
        );
    }

    private SwingButton createSaveButton() {
        Dimension saveButtonSize = new Dimension(
                config.getInt("SAVE_TEXT_BUTTON_WIDTH"),
                config.getInt("SAVE_TEXT_BUTTON_HEIGHT")
        );

        return new SwingButton(
                config.getString("SAVE_TEXT_BUTTON_TEXT"),
                null,
                config.getScaledInt("SAVE_TEXT_BUTTON_FONT_SIZE"),
                null,
                null
        );
    }

    private SwingButton createLoadButton() {
        Dimension loadButtonSize = new Dimension(
                config.getInt("LOAD_TEXT_BUTTON_WIDTH"),
                config.getInt("LOAD_TEXT_BUTTON_HEIGHT")
        );

        return new SwingButton(
                config.getString("LOAD_TEXT_BUTTON_TEXT"),
                null,
                config.getScaledInt("LOAD_TEXT_BUTTON_FONT_SIZE"),
                null,
                null
        );
    }

    private TrackSelectorPanel createTrackSelector() {
        List<String> options = new ArrayList<>();
        int i = 0;
        while (config.getKeys().contains("TRACK_SELECTOR_OPTION_" + i)) {
            options.add(config.getString("TRACK_SELECTOR_OPTION_" + i));
            i++;
        }

        SwingLabel label = new SwingLabel(
                config.getString("TRACK_SELECTOR_LABEL_TEXT"),
                null,
                config.getScaledInt("TRACK_SELECTOR_LABEL_FONT_SIZE"),
                null
        );

        return new TrackSelectorPanel(
                label,
                options,
                config.getString("TRACK_SELECTOR_STARTING_OPTION"),
                null,
                config.getScaledInt("TRACK_SELECTOR_FONT_SIZE"),
                null
        );
    }

    private ParameterSliderPanel createVolumePanel() {
        SwingSlider volumeSlider = new SwingSlider(
                config.getInt("VOLUME_SLIDER_DIRECTION"),
                config.getInt("VOLUME_SLIDER_MIN"),
                config.getInt("VOLUME_SLIDER_MIN_TO_SHOW"),
                config.getInt("VOLUME_SLIDER_MAX"),
                config.getInt("VOLUME_SLIDER_MAX_TO_SHOW"),
                config.getInt("VOLUME_SLIDER_MAX_TO_SHOW")/2, // Middle of the slider
                config.getInt("VOLUME_SLIDER_TICK_SPACING"),
                null,
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );

        SwingLabel volumeLabel = new SwingLabel(config.getScaledInt("PARAMETER_LABEL_FONT_SIZE"));

        return new ParameterSliderPanel(
                volumeSlider,
                volumeLabel,
                config.getString("VOLUME_LABEL_TEXT"),
                config.getScaledDimension("PARAMETER_PANEL_PREFERRED_SIZE"),
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );
    }

    private ParameterSliderPanel createOctavePanel() {
        SwingSlider octaveSlider = new SwingSlider(
                config.getInt("OCTAVE_SLIDER_DIRECTION"),
                config.getInt("OCTAVE_SLIDER_MIN"),
                config.getInt("OCTAVE_SLIDER_MIN_TO_SHOW"),
                config.getInt("OCTAVE_SLIDER_MAX"),
                config.getInt("OCTAVE_SLIDER_MAX_TO_SHOW"),
                config.getInt("OCTAVE_SLIDER_MAX_TO_SHOW")/2, // Middle of the slider
                config.getInt("OCTAVE_SLIDER_TICK_SPACING"),
                null,
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );

        SwingLabel octaveLabel = new SwingLabel(config.getScaledInt("PARAMETER_LABEL_FONT_SIZE"));

        return new ParameterSliderPanel(
                octaveSlider,
                octaveLabel,
                config.getString("OCTAVE_LABEL_TEXT"),
                config.getScaledDimension("PARAMETER_PANEL_PREFERRED_SIZE"),
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );
    }

    private ParameterSliderPanel createBpmPanel() {
        SwingSlider bpmSlider = new SwingSlider(
                config.getInt("BPM_SLIDER_DIRECTION"),
                config.getInt("BPM_SLIDER_MIN"),
                config.getInt("BPM_SLIDER_MIN_TO_SHOW"),
                config.getInt("BPM_SLIDER_MAX"),
                config.getInt("BPM_SLIDER_MAX_TO_SHOW"),
                config.getInt("BPM_SLIDER_MAX_TO_SHOW")/2, // Middle of the slider
                config.getInt("BPM_SLIDER_TICK_SPACING"),
                null,
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );

        SwingLabel bpmLabel = new SwingLabel(config.getScaledInt("PARAMETER_LABEL_FONT_SIZE"));

        return new ParameterSliderPanel(
                bpmSlider,
                bpmLabel,
                config.getString("BPM_LABEL_TEXT"),
                config.getScaledDimension("PARAMETER_PANEL_PREFERRED_SIZE"),
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );
    }

    private ParameterComboBoxPanel<String> createInstrumentPanel() {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < 128; i++) {
            items.add(Integer.toString(i));
        }

        SwingComboBox<String> instrumentComboBox = new SwingComboBox<>(
                items,
                null,
                config.getScaledInt("INSTRUMENT_COMBOBOX_FONT_SIZE"),
                null,
                config.getInt("INSTRUMENT_COMBOBOX_INITIAL_INDEX"),
                config.getBoolean("INSTRUMENT_COMBOBOX_IS_EDITABLE"),
                null,
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );
        instrumentComboBox.enableFiltering();

        SwingLabel instrumentLabel = new SwingLabel(
                config.getString("INSTRUMENT_LABEL_TEXT"),
                null,
                config.getScaledInt("PARAMETER_LABEL_FONT_SIZE"),
                null
        );

        return new ParameterComboBoxPanel<>(
                instrumentComboBox,
                instrumentLabel,
                config.getScaledDimension("PARAMETER_PANEL_PREFERRED_SIZE"),
                config.getScaledDimension("PARAMETER_PANEL_MAX_SIZE")
        );
    }
}