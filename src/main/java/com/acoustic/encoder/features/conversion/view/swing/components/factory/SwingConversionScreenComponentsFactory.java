package com.acoustic.encoder.features.conversion.view.swing.components.factory;

import com.acoustic.encoder.features.conversion.view.swing.components.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.Button;

import javax.swing.*;
import java.awt.*;

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

    @Override
    public ConversionScreenComponentsWrapper createComponents() {

        Object conversionButton = new Button(CONVERTER_BUTTON_TEXT);
        Object textArea = createTextArea();
        Object scrollPane = createScrollPane();
        Object instructionLabel = createInstructionLabel();
        Object volumePanel = createVolumePanel();
        Object octavePanel = createOctavePanel();
        Object instrumentPanel = createInstrumentPanel();
        Object bpmPanel = createBpmPanel();

        return new ConversionScreenComponentsWrapper(
                conversionButton,
                textArea,
                scrollPane,
                instructionLabel,
                volumePanel,
                octavePanel,
                instrumentPanel,
                bpmPanel
        );
    }

    private static JLabel createInstructionLabel() {

        JLabel instruction = new JLabel(INSTRUCTION_LABEL_TEXT);

        instruction.setBorder(BorderFactory.createEmptyBorder(
                INSTRUCTION_LABEL_TGAP,
                INSTRUCTION_LABEL_LGAP,
                INSTRUCTION_LABEL_BGAP,
                INSTRUCTION_LABEL_RGAP
        ));

        return instruction;
    }

    private static JTextArea createTextArea() {

        JTextArea textArea = new JTextArea();

        // Breaks the text automatically when it reaches the border
        textArea.setLineWrap(true);

        // Prevents the text from being cut in the middle
        textArea.setWrapStyleWord(true);

        return textArea;
    }

    private static JScrollPane createScrollPane() {

        // Creates a scrollable text area
        JScrollPane scrollPane = new JScrollPane(createTextArea());

        scrollPane.setBorder(BorderFactory.createEmptyBorder(
                MAIN_SCROLL_TEXTAREA_TGAP,
                MAIN_SCROLL_TEXTAREA_LGAP,
                MAIN_SCROLL_TEXTAREA_BGAP,
                MAIN_SCROLL_TEXTAREA_RGAP
        ));

        return scrollPane;
    }

    private JPanel createVolumePanel() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JSlider slider = createSlider(JSlider.HORIZONTAL, 0, 127, 64);
        slider.setMajorTickSpacing(32);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        label.setText("Volume: " + slider.getValue());

        panel.add(slider);
        panel.add(label);

        return panel;
    }

    private JPanel createOctavePanel() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JSlider slider = createSlider(JSlider.HORIZONTAL, 1, 10, 5);
        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        label.setText("Octave: " + slider.getValue());

        panel.add(slider);
        panel.add(label);

        return panel;
    }

    private JPanel createInstrumentPanel() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JSlider slider = createSlider(JSlider.HORIZONTAL, 0, 127, 0);
        slider.setMajorTickSpacing(32);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        label.setText("Instrument: " + slider.getValue());

        panel.add(slider);
        panel.add(label);

        return panel;
    }

    private JPanel createBpmPanel() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JSlider slider = createSlider(JSlider.HORIZONTAL, 10, 1000, 120);
        slider.setMajorTickSpacing(250);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        label.setText("BPM: " + slider.getValue());

        panel.add(slider);
        panel.add(label);

        return panel;
    }

    private JSlider createSlider(int direction, int min, int max, int startValue) {

        JSlider slider = new JSlider(direction, min, max, startValue);

        return slider;
    }
}