package com.acoustic.encoder.gui;

import com.acoustic.encoder.controller.ConversionController;
import com.acoustic.encoder.model.UserConversionInput;

import javax.swing.*;
import java.awt.*;

public class MainScreen {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int WINDOW_HEIGHT = 400;
    private final static int WINDOW_WIDTH = 500;

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static String INSTRUCTION_LABEL_TEXT = "Write the text you want to convert to sound:";
    private final static int INSTRUCTION_LABEL_TGAP = 10;
    private final static int INSTRUCTION_LABEL_LGAP = 10;
    private final static int INSTRUCTION_LABEL_BGAP = 0;
    private final static int INSTRUCTION_LABEL_RGAP = 10;

    private final static int MAIN_SCROLL_TEXTAREA_TGAP = 0;
    private final static int MAIN_SCROLL_TEXTAREA_LGAP = 10;
    private final static int MAIN_SCROLL_TEXTAREA_BGAP = 0;
    private final static int MAIN_SCROLL_TEXTAREA_RGAP = 10;

    private final static String CONVERTER_BUTTON_TEXT = "Convert to Sound!";

    private final static String EMPTY_INPUT_WARNING = "Please enter some text first!";

    private static JFrame frame;

    private final ConversionController conversionController;

    int defaultVolume = 64;
    int defaultOctave = 5;
    int defaultInstrument = 0;
    int defaultBpm = 120;

    public MainScreen(ConversionController conversionController) {

        if (frame == null)  frame = new JFrame(WINDOW_TITLE);

        if (conversionController == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.conversionController = conversionController;
    }

    public void startFrame() {

        // Sets the window to close when the user clicks the close button.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Divides the window in NORTH, SOUTH, EAST, WEST and CENTER.
        // The numbers (BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP) are the gap between the areas.
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        JLabel instructionLabel = createInstructionLabel();

        JTextArea textArea = createMainTextArea();

        JScrollPane scrollTextArea = createMainScrollTextArea(textArea);

        JButton converterButton = createConverterButton(textArea);

        // Adding components to the frame
        frame.add(instructionLabel, BorderLayout.NORTH);
        frame.add(scrollTextArea, BorderLayout.CENTER);
        frame.add(converterButton, BorderLayout.SOUTH);
        frame.add(createParameterPanel(), BorderLayout.EAST);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

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

    private static JTextArea createMainTextArea() {

        JTextArea textArea = new JTextArea();

        // Breaks the text automatically when it reaches the border
        textArea.setLineWrap(true);

        // Prevents the text from being cut in the middle
        textArea.setWrapStyleWord(true);

        return textArea;
    }

    private static JScrollPane createMainScrollTextArea(JTextArea textArea) {

        // Creates a scrollable text area
        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setBorder(BorderFactory.createEmptyBorder(
                MAIN_SCROLL_TEXTAREA_TGAP,
                MAIN_SCROLL_TEXTAREA_LGAP,
                MAIN_SCROLL_TEXTAREA_BGAP,
                MAIN_SCROLL_TEXTAREA_RGAP
        ));

        return scrollPane;
    }

    private JButton createConverterButton(JTextArea textArea) {

        JButton converterButton = new JButton(CONVERTER_BUTTON_TEXT);

        converterButton.addActionListener(event -> {

            if (event.getSource() != converterButton) return;

            try {

                this.conversionController.handleConvertAction(
                        new UserConversionInput(
                                textArea.getText(),
                                defaultInstrument,
                                defaultBpm,
                                defaultOctave,
                                defaultVolume
                        )
                );
            }
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, EMPTY_INPUT_WARNING);
                return;
            }

            // WORK IN PROGRESS
            // ----------------------------------------------------------------------------------
            JOptionPane.showMessageDialog(frame, "Maybe im converting your text to sound!");
            // ----------------------------------------------------------------------------------
        });

        return converterButton;
    }

    private JPanel createParameterPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4, 1));

        panel.add(createVolumePanel());
        panel.add(createOctavePanel());
        panel.add(createInstrumentPanel());
        panel.add(createBpmPanel());

        return panel;
    }

    private JPanel createVolumePanel() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JSlider slider = createSlider(JSlider.HORIZONTAL, 0, 127, 64);
        slider.setMajorTickSpacing(32);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(e -> {
            label.setText("Volume: " + slider.getValue());
            defaultVolume = slider.getValue();
        });

        label.setText("Volume: " + slider.getValue());

        panel.add(slider);
        panel.add(label);

        return panel;
    }

    private JPanel createOctavePanel() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        JSlider slider = createSlider(JSlider.HORIZONTAL, 0, 9, 5);
        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(e -> {
            label.setText("Octave: " + slider.getValue());
            defaultOctave = slider.getValue();
        });

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

        slider.addChangeListener(e -> {
            label.setText("Instrument: " + slider.getValue());
            defaultInstrument = slider.getValue();
        });

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

        slider.addChangeListener(e -> {
            label.setText("BPM: " + slider.getValue());
            defaultBpm = slider.getValue();
        });

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

