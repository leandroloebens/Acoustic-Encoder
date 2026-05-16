package com.acoustic.encoder.features.conversion.ui.swing.components.factory;

import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.ui.swing.components.VoiceSelectorPanel;
import com.acoustic.encoder.domain.music.InstrumentOption;
import com.acoustic.encoder.infrastructure.ui_shared.swing.SwingViewConfigWrapper;
import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.features.conversion.ui.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.*;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class DefaultSwingConversionViewComponentsFactory implements SwingConversionViewComponentsFactory {

    private final static String ILLEGAL_CONFIG_ARGUMENT_MESSAGE = "Illegal config argument!";
    private final static String ILLEGAL_INSTRUMENT_PROVIDER_ARGUMENT_MESSAGE = "Illegal instrument provider argument!";
    private final static String INSTRUMENT_PROVIDER_FAILED_MSG = "Instrument provider failed!";

    private final static int SCROLL_PANE_MAX_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.6);

    private final SwingViewConfigWrapper config;

    private final InstrumentListProvider instrumentProvider;

    public DefaultSwingConversionViewComponentsFactory(
            HashMap<String, String> configMap,
            InstrumentListProvider instrumentProvider
    ) {
        if (configMap == null) throw new IllegalArgumentException(ILLEGAL_CONFIG_ARGUMENT_MESSAGE);
        this.config = new SwingViewConfigWrapper(configMap);

        if (instrumentProvider == null)
            throw new IllegalArgumentException(ILLEGAL_INSTRUMENT_PROVIDER_ARGUMENT_MESSAGE);
        this.instrumentProvider = instrumentProvider;
    }

    @Override
    public ConversionViewSwingComponentsWrapper createComponents() {

        SwingButton conversionButton = createConverterButton();

        SwingButton saveTextButton = createSaveButton();

        SwingButton loadTextButton = createLoadButton();

        SwingUtils.setHandCursor(conversionButton, saveTextButton, loadTextButton);

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

        ParameterSliderPanel bpmPanel = createBpmPanel();

        VoiceSelectorPanel voiceSelector = createVoiceSelector();

        ParameterSliderPanel volumePanel = createVolumePanel();

        ParameterSliderPanel octavePanel = createOctavePanel();

        SwingUtils.setHandCursor(volumePanel.getSlider(), octavePanel.getSlider(), bpmPanel.getSlider());

        ParameterComboBoxPanel<InstrumentOption> instrumentPanel = createInstrumentPanel();

        return new ConversionViewSwingComponentsWrapper(
                conversionButton,
                saveTextButton,
                loadTextButton,
                scrollPane,
                instructionLabel,
                voiceSelector,
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

    private VoiceSelectorPanel createVoiceSelector() {
        List<String> options = new ArrayList<>();
        int i = 0;
        while (config.getKeys().contains("VOICE_SELECTOR_OPTION_" + i)) {
            options.add(config.getString("VOICE_SELECTOR_OPTION_" + i));
            i++;
        }

        SwingLabel label = new SwingLabel(
                config.getString("VOICE_SELECTOR_LABEL_TEXT"),
                null,
                config.getScaledInt("VOICE_SELECTOR_LABEL_FONT_SIZE"),
                null
        );

        return new VoiceSelectorPanel(
                label,
                options,
                config.getString("VOICE_SELECTOR_STARTING_OPTION"),
                null,
                config.getScaledInt("VOICE_SELECTOR_FONT_SIZE"),
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

    private ParameterComboBoxPanel<InstrumentOption> createInstrumentPanel() {
        List<InstrumentOption> instrumentOptions = new ArrayList<>();

        List<InstrumentOption> instruments = instrumentProvider.getInstrumentList();
        instruments.sort(Comparator.comparingInt(InstrumentOption::id));

        int expectedId = 0;
        for (InstrumentOption instrument : instruments) {
            String instrumentName = instrument.name().trim();

            if (
                instrumentName.isEmpty()
                || instrumentOptions.contains(instrument)
                || instrument.id() != expectedId
            ) continue;

            instrumentOptions.add(new InstrumentOption(instrumentName, instrument.id()));
            expectedId++;
        }

        SwingComboBox<InstrumentOption> instrumentComboBox = new SwingComboBox<>(
                instrumentOptions,
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