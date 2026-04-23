package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.view.swing.components.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.*;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingConversionScreenAssembler implements SwingConversionScreenAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static String EMPTY_INPUT_WARNING = "Please enter some text first!";

    private final SwingButton converterButton;
    private final SwingLabel instructionLabel;
    private final SwingVerticalScrollPane scrollPane;
    private final ParameterPanel volumePanel;
    private final ParameterPanel octavePanel;
    private final ParameterPanel instrumentPanel;
    private final ParameterPanel bpmPanel;

    public DefaultSwingConversionScreenAssembler(ConversionScreenComponentsWrapper components) {
        this.converterButton = components.converterButton();
        this.instructionLabel = components.instructionLabel();
        this.scrollPane = components.scrollPane();
        this.volumePanel = components.volumePanel();
        this.octavePanel = components.octavePanel();
        this.instrumentPanel = components.instrumentPanel();
        this.bpmPanel = components.bpmPanel();
    }

    @Override
    public String getInputText() {

        SwingTextArea textArea = (SwingTextArea) this.scrollPane.getComponent();

        return textArea.getText();
    }

    @Override
    public SwingFrame assemble(
            String title,
            int windowWidth,
            int windowHeight,
            Runnable conversionAction,
            Runnable volumeSliderAction,
            Runnable instrumentSliderAction,
            Runnable octaveSliderAction,
            Runnable bpmSliderAction
    ) {

        SwingFrame frame = new SwingFrame(title, windowWidth, windowHeight);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        setSliderToVolume(volumePanel, volumeSliderAction);
        setSliderToOctave(octavePanel, octaveSliderAction);
        setSliderToInstrument(instrumentPanel, instrumentSliderAction);
        setSliderToBpm(bpmPanel, bpmSliderAction);

        SwingPanel configPanel = new SwingPanel(new GridLayout(4, 1));
        configPanel.add(volumePanel);
        configPanel.add(octavePanel);
        configPanel.add(instrumentPanel);
        configPanel.add(bpmPanel);

        frame.add(instructionLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(converterButton, BorderLayout.SOUTH);
        frame.add(configPanel, BorderLayout.EAST);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        converterButton.addActionListener(event -> {
            if (event.getSource() != converterButton) return;

            
            try {
                
                if (getInputText().isEmpty()) throw new IllegalArgumentException();
                else conversionAction.run();

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(frame, EMPTY_INPUT_WARNING);
                return;
            }
            

            // WORK IN PROGRESS
            // ----------------------------------------------------------------------------------
            JOptionPane.showMessageDialog(frame, "Maybe im converting your text to sound!");
            // ----------------------------------------------------------------------------------
        });

        return frame;
    }

    @Override
    public int getVolumeSliderValue() { return volumePanel.getSlider().getValue(); }

    @Override
    public int getInstrumentSliderValue() { return instrumentPanel.getSlider().getValue(); }

    @Override
    public int getOctaveSliderValue() { return octavePanel.getSlider().getValue(); }

    @Override
    public int getBpmSliderValue() { return bpmPanel.getSlider().getValue(); }

    private void setSliderToVolume(ParameterPanel panel, Runnable volumeSliderAction) {
        SwingSlider slider = panel.getSlider();

        slider.addChangeListener(e -> {
            panel.getLabel().setText("Volume: " + slider.getValue());
            volumeSliderAction.run();
        });
    }

    private void setSliderToOctave(ParameterPanel panel, Runnable octaveSliderAction) {
        SwingSlider slider = panel.getSlider();

        slider.addChangeListener(e -> {
            panel.getLabel().setText("Octave: " + slider.getValue());
            octaveSliderAction.run();
        });
    }

    private void setSliderToInstrument(ParameterPanel panel, Runnable instrumentSliderAction) {
        SwingSlider slider = panel.getSlider();

        slider.addChangeListener(e -> {
            panel.getLabel().setText("Instrument: " + slider.getValue());
            instrumentSliderAction.run();
        });
    }

    private void setSliderToBpm(ParameterPanel panel, Runnable bpmSliderAction) {
        SwingSlider slider = panel.getSlider();

        slider.addChangeListener(e -> {
            panel.getLabel().setText("BPM: " + slider.getValue());
            bpmSliderAction.run();
        });
    }
}

