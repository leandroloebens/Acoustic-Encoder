package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.view.ConversionScreenComponentsAssembler;
import com.acoustic.encoder.features.conversion.view.swing.components.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.shared.model.MusicConfig;

import javax.swing.*;
import java.awt.*;

public class SwingConversionScreenAssembler implements ConversionScreenComponentsAssembler {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static String EMPTY_INPUT_WARNING = "Please enter some text first!";

    private int defaultVolume;
    private int defaultOctave;
    private int defaultInstrument;
    private int defaultBpm;

    private Runnable conversionAction;

    private final JFrame frame = new JFrame(WINDOW_TITLE);
    private final JButton converterButton;
    private final JLabel instructionLabel;
    private JTextArea textArea;
    private final JScrollPane scrollPane;
    private final JPanel volumePanel;
    private final JPanel octavePanel;
    private final JPanel instrumentPanel;
    private final JPanel bpmPanel;
    private final JPanel parameterPanel = new JPanel();

    public SwingConversionScreenAssembler(ConversionScreenComponentsWrapper components) {
        this.converterButton = (JButton) components.converterButton();
        this.instructionLabel = (JLabel) components.instructionLabel();
        this.scrollPane = (JScrollPane) components.scrollPane();
        this.volumePanel = (JPanel) components.volumePanel();
        this.octavePanel = (JPanel) components.octavePanel();
        this.instrumentPanel = (JPanel) components.instrumentPanel();
        this.bpmPanel = (JPanel) components.bpmPanel();
    }

    @Override
    public void setConversionAction(Runnable action) {
        this.conversionAction = action;
    }

    @Override
    public void setDefaultParameters(MusicConfig parameters) {
        this.defaultVolume = parameters.defaultVolume();
        this.defaultOctave = parameters.defaultOctave();
        this.defaultInstrument = parameters.defaultMidiInstrument();
        this.defaultBpm = parameters.bpm();
    }

    @Override
    public MusicConfig getDefaultParameters() {
        return new MusicConfig(defaultInstrument, defaultBpm, defaultOctave, defaultVolume);
    }

    @Override
    public String getInputText() {
        return this.textArea.getText();
    }

    @Override
    public void assemble(int windowWidth, int windowHeight) {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        setSliderToVolume(volumePanel);
        setSliderToOctave(octavePanel);
        setSliderToInstrument(instrumentPanel);
        setSliderToBpm(bpmPanel);

        parameterPanel.setLayout(new GridLayout(4, 1));
        parameterPanel.add(volumePanel);
        parameterPanel.add(octavePanel);
        parameterPanel.add(instrumentPanel);
        parameterPanel.add(bpmPanel);

        textArea = getTextAreaFromScrollPane(scrollPane);

        frame.add(instructionLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(converterButton, BorderLayout.SOUTH);
        frame.add(parameterPanel, BorderLayout.EAST);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        converterButton.addActionListener(event -> {
            if (event.getSource() != converterButton) return;

            try {

                conversionAction.run();

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
    }

    @Override
    public void showFrame() {
        this.frame.setVisible(true);
    }

    @Override
    public void hideFrame() {
        this.frame.setVisible(false);
    }

    // @Override
    // public void destroyFrame() { this.frame.dispose();}

    private void setSliderToVolume(JPanel panel) {
        JSlider slider = getSlider(panel);
        JLabel label = getLabel(panel);

        slider.addChangeListener(e -> {
            label.setText("Volume: " + slider.getValue());
            defaultVolume = slider.getValue();
        });
    }

    private void setSliderToOctave(JPanel panel) {
        JSlider slider = getSlider(panel);
        JLabel label = getLabel(panel);

        slider.addChangeListener(e -> {
            label.setText("Octave: " + slider.getValue());
            defaultOctave = slider.getValue();
        });
    }

    private void setSliderToInstrument(JPanel panel) {
        JSlider slider = getSlider(panel);
        JLabel label = getLabel(panel);

        slider.addChangeListener(e -> {
            label.setText("Instrument: " + slider.getValue());
            defaultInstrument = slider.getValue();
        });
    }

    private void setSliderToBpm(JPanel panel) {
        JSlider slider = getSlider(panel);
        JLabel label = getLabel(panel);

        slider.addChangeListener(e -> {
            label.setText("BPM: " + slider.getValue());
            defaultBpm = slider.getValue();
        });
    }

    private JLabel getLabel (JPanel panel) {
        JLabel label = null;

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                label = (JLabel) comp;
                break; // found the first JSlider
            }
        }

        return label;
    }

    private JSlider getSlider(JPanel panel) {
        JSlider slider = null;

        for (Component comp : panel.getComponents()) {
            if (comp instanceof JSlider) {
                slider = (JSlider) comp;
                break; // found the first JSlider
            }
        }

        return slider;
    }

    private static JTextArea getTextAreaFromScrollPane(JScrollPane scrollPane) {
        java.awt.Component view = scrollPane.getViewport().getView();
        if (view instanceof JTextArea) {
            return (JTextArea) view;
        }
        return null; // or throw an exception if you want to enforce it
    }
}

