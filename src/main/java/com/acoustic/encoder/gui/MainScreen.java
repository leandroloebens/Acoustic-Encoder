package com.acoustic.encoder.gui;

import com.acoustic.encoder.controller.AppController;

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

    private final AppController controller;

    public MainScreen(AppController controller) {

        if (frame == null)  frame = new JFrame(WINDOW_TITLE);

        if (controller == null) throw new IllegalArgumentException("Controller cannot be null!");
        this.controller = controller;
    }

    public void startFrame() {

        // Sets the window to close when the user clicks the close button.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Divides the window in NORTH, SOUTH, EAST, WEST and CENTER.
        // The numbers (BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP) are the gap between the areas.
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        JLabel instruction = createInstructionLabel();

        JTextArea textArea = createMainTextArea();

        JScrollPane scrollTextArea = createMainScrollTextArea(textArea);

        JButton converterButton = createConverterButton(textArea);

        // Adding components to the frame
        frame.add(instruction, BorderLayout.NORTH);
        frame.add(scrollTextArea, BorderLayout.CENTER);
        frame.add(converterButton, BorderLayout.SOUTH);

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
                this.controller.onConvertButtonClick(textArea.getText());
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

}

