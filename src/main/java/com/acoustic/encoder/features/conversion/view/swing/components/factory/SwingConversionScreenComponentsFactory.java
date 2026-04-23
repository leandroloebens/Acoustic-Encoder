package com.acoustic.encoder.features.conversion.view.swing.components.factory;

import com.acoustic.encoder.features.conversion.view.swing.components.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.*;

import javax.swing.*;
import javax.swing.border.Border;

public class SwingConversionScreenComponentsFactory implements ConversionScreenComponentsFactory {

    private final static String CONVERTER_BUTTON_TEXT = "Convert to Sound!";

    private final static String INSTRUCTION_LABEL_TEXT = "Write the text you want to convert to sound:";
    private final static int INSTRUCTION_LABEL_TGAP = 10;
    private final static int INSTRUCTION_LABEL_LGAP = 10;
    private final static int INSTRUCTION_LABEL_BGAP = 0;
    private final static int INSTRUCTION_LABEL_RGAP = 10;

    private final static int MAIN_SCROLL_TEXTAREA_TGAP = 0;
    private final static int MAIN_SCROLL_TEXTAREA_LGAP = 10;
    private final static int MAIN_SCROLL_TEXTAREA_BGAP = 0;
    private final static int MAIN_SCROLL_TEXTAREA_RGAP = 10;

    private final static int VOLUME_SLIDER_DIRECTION = JSlider.HORIZONTAL;
    private final static int VOLUME_SLIDER_MIN = 0;
    private final static int VOLUME_SLIDER_MAX = 127;
    private final static int VOLUME_SLIDER_VALUE = 64;
    private final static int VOLUME_SLIDER_TICK_SPACING = 32;
    private final static boolean VOLUME_SLIDER_SHOW_LABELS = true;

    private final static int OCTAVE_SLIDER_DIRECTION = JSlider.HORIZONTAL;
    private final static int OCTAVE_SLIDER_MIN = 1;
    private final static int OCTAVE_SLIDER_MAX = 10;
    private final static int OCTAVE_SLIDER_VALUE = 5;
    private final static int OCTAVE_SLIDER_TICK_SPACING = 2;
    private final static boolean OCTAVE_SLIDER_SHOW_LABELS = true;

    private final static int INSTRUMENT_SLIDER_DIRECTION = JSlider.HORIZONTAL;
    private final static int INSTRUMENT_SLIDER_MIN = 0;
    private final static int INSTRUMENT_SLIDER_MAX = 127;
    private final static int INSTRUMENT_SLIDER_VALUE = 0;
    private final static int INSTRUMENT_SLIDER_TICK_SPACING = 32;
    private final static boolean INSTRUMENT_SLIDER_SHOW_LABELS = true;

    private final static int BPM_SLIDER_DIRECTION = JSlider.HORIZONTAL;
    private final static int BPM_SLIDER_MIN = 10;
    private final static int BPM_SLIDER_MAX = 1000;
    private final static int BPM_SLIDER_VALUE = 120;
    private final static int BPM_SLIDER_TICK_SPACING = 250;
    private final static boolean BPM_SLIDER_SHOW_LABELS = true;


    @Override
    public ConversionScreenComponentsWrapper createComponents() {

        SwingButton conversionButton = new SwingButton(CONVERTER_BUTTON_TEXT, null, null);

        SwingTextArea textArea = new SwingTextArea(null, null);
        Border scrollPaneBorder = BorderFactory.createEmptyBorder(
                MAIN_SCROLL_TEXTAREA_TGAP,
                MAIN_SCROLL_TEXTAREA_LGAP,
                MAIN_SCROLL_TEXTAREA_BGAP,
                MAIN_SCROLL_TEXTAREA_RGAP
        );
        SwingVerticalScrollPane scrollPane = new SwingVerticalScrollPane(textArea, scrollPaneBorder);

        Border instructionLabelBorder = BorderFactory.createEmptyBorder(
                INSTRUCTION_LABEL_TGAP,
                INSTRUCTION_LABEL_LGAP,
                INSTRUCTION_LABEL_BGAP,
                INSTRUCTION_LABEL_RGAP
        );
        SwingLabel instructionLabel = new SwingLabel(INSTRUCTION_LABEL_TEXT, null, instructionLabelBorder);

        ParameterPanel volumePanel = createVolumePanel();

        ParameterPanel octavePanel = createOctavePanel();

        ParameterPanel instrumentPanel = createInstrumentPanel();

        ParameterPanel bpmPanel = createBpmPanel();

        return new ConversionScreenComponentsWrapper(
                conversionButton,
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
                VOLUME_SLIDER_DIRECTION,
                VOLUME_SLIDER_MIN,
                VOLUME_SLIDER_MAX,
                VOLUME_SLIDER_VALUE,
                VOLUME_SLIDER_TICK_SPACING,
                VOLUME_SLIDER_SHOW_LABELS
        );

        String labelText = "Volume: " + volumeSlider.getValue();
        SwingLabel volumeLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, volumeSlider, volumeLabel);
    }

    private ParameterPanel createOctavePanel() {
        SwingSlider octaveSlider = new SwingSlider(
                OCTAVE_SLIDER_DIRECTION,
                OCTAVE_SLIDER_MIN,
                OCTAVE_SLIDER_MAX,
                OCTAVE_SLIDER_VALUE,
                OCTAVE_SLIDER_TICK_SPACING,
                OCTAVE_SLIDER_SHOW_LABELS
        );

        String labelText = "Octave: " + octaveSlider.getValue();
        SwingLabel octaveLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, octaveSlider, octaveLabel);
    }

    private ParameterPanel createInstrumentPanel() {
        SwingSlider instrumentSlider = new SwingSlider(
                INSTRUMENT_SLIDER_DIRECTION,
                INSTRUMENT_SLIDER_MIN,
                INSTRUMENT_SLIDER_MAX,
                INSTRUMENT_SLIDER_VALUE,
                INSTRUMENT_SLIDER_TICK_SPACING,
                INSTRUMENT_SLIDER_SHOW_LABELS
        );

        String labelText = "Instrument: " + instrumentSlider.getValue();
        SwingLabel instrumentLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, instrumentSlider, instrumentLabel);
    }

    private ParameterPanel createBpmPanel() {
        SwingSlider bpmSlider = new SwingSlider(
                BPM_SLIDER_DIRECTION,
                BPM_SLIDER_MIN,
                BPM_SLIDER_MAX,
                BPM_SLIDER_VALUE,
                BPM_SLIDER_TICK_SPACING,
                BPM_SLIDER_SHOW_LABELS
        );

        String labelText = "BPM: " + bpmSlider.getValue();
        SwingLabel bpmLabel = new SwingLabel(labelText, null, null);

        return new ParameterPanel(null, bpmSlider, bpmLabel);
    }
}