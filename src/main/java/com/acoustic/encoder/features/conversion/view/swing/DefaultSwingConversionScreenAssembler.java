package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionScreenComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DefaultSwingConversionScreenAssembler implements SwingConversionScreenAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static String EMPTY_INPUT_WARNING = "Please enter some text first!";

    private final SwingButton converterButton;
    private final SwingButton saveButton;
    private final SwingButton loadButton;
    private final SwingLabel instructionLabel;
    private final SwingVerticalScrollPane scrollPane;
    private final ParameterPanel volumePanel;
    private final ParameterPanel octavePanel;
    private final ParameterPanel instrumentPanel;
    private final ParameterPanel bpmPanel;

    public DefaultSwingConversionScreenAssembler(ConversionScreenComponentsWrapper components) {
        this.converterButton = components.converterButton();
        this.saveButton = components.saveTextButton();
        this.loadButton = components.loadTextButton();
        this.instructionLabel = components.instructionLabel();
        this.scrollPane = components.scrollPane();
        this.volumePanel = components.volumePanel();
        this.octavePanel = components.octavePanel();
        this.instrumentPanel = components.instrumentPanel();
        this.bpmPanel = components.bpmPanel();
    }

    @Override
    public SwingFrame assembleFrame(
            String title,
            int windowWidth,
            int windowHeight,
            int frameExitOperation,
            SwingEventHandler handler
    ) {

        SwingFrame frame = new SwingFrame(title, windowWidth, windowHeight, frameExitOperation);

        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        setSlidersToParameters(
                handler,
                volumePanel,
                octavePanel,
                instrumentPanel,
                bpmPanel
        );

        SwingPanel configPanel = new SwingPanel(new GridLayout(4, 1));
        configPanel.add(volumePanel);
        configPanel.add(octavePanel);
        configPanel.add(instrumentPanel);
        configPanel.add(bpmPanel);

        // frame.add(instructionLabel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(converterButton, BorderLayout.SOUTH);
        frame.add(saveButton, BorderLayout.WEST);
        frame.add(loadButton, BorderLayout.NORTH);
        frame.add(configPanel, BorderLayout.EAST);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        setButtonsActions(handler, frame);

        return frame;
    }

    @Override
    public String getInputText() {

        SwingTextArea textArea = (SwingTextArea) this.scrollPane.getComponent();

        return textArea.getText();
    }

    @Override
    public void setInputText(String text) {
        SwingTextArea textArea = (SwingTextArea) this.scrollPane.getComponent();
        textArea.setText(text);
    }

    @Override
    public int getVolumeSliderValue() { return volumePanel.getSlider().getValue(); }

    @Override
    public int getInstrumentSliderValue() { return instrumentPanel.getSlider().getValue(); }

    @Override
    public int getOctaveSliderValue() { return octavePanel.getSlider().getValue(); }

    @Override
    public int getBpmSliderValue() { return bpmPanel.getSlider().getValue(); }

    private void setButtonsActions(SwingEventHandler handler, SwingFrame frame) {
        converterButton.addActionListener(event -> {
            if (event.getSource() != converterButton) return;

            try {

                if (getInputText().isEmpty()) throw new IllegalArgumentException();
                else handler.onConvert();

            } catch (IllegalArgumentException e) {

                JOptionPane.showMessageDialog(frame, EMPTY_INPUT_WARNING);

            }

            // WORK IN PROGRESS
            // ----------------------------------------------------------------------------------
            // JOptionPane.showMessageDialog(frame, "Maybe im converting your text to sound!");
            // ----------------------------------------------------------------------------------
        });



        saveButton.addActionListener(event -> {
            if (event.getSource() != saveButton) return;

            handler.onSave();

        });

        loadButton.addActionListener(event -> {
            if (event.getSource() != loadButton) return;

            handler.onLoad();

        });
    }

    private void setSlidersToParameters(
            SwingEventHandler handler,
            ParameterPanel volumePanel,
            ParameterPanel octavePanel,
            ParameterPanel instrumentPanel,
            ParameterPanel bpmPanel
            ) {

        setSliderToVolume(volumePanel, handler);
        setSliderToOctave(octavePanel, handler);
        setSliderToInstrument(instrumentPanel, handler);
        setSliderToBpm(bpmPanel, handler);

    }

    private void setSliderToVolume(ParameterPanel panel, SwingEventHandler handler) {

        panel.getSlider().addChangeListener(e -> {
            panel.getLabel().setText("Volume: " + panel.getSlider().getValue());
            handler.onVolumeChange();
        });

    }

    private void setSliderToOctave(ParameterPanel panel, SwingEventHandler handler) {

        panel.getSlider().addChangeListener(e -> {
            panel.getLabel().setText("Octave: " + panel.getSlider().getValue());
            handler.onOctaveChange();
        });

    }

    private void setSliderToInstrument(ParameterPanel panel, SwingEventHandler handler) {

        panel.getSlider().addChangeListener(e -> {
            panel.getLabel().setText("Instrument: " + panel.getSlider().getValue());
            handler.onInstrumentChange();
        });

    }

    private void setSliderToBpm(ParameterPanel panel, SwingEventHandler handler) {

        panel.getSlider().addChangeListener(e -> {
            panel.getLabel().setText("BPM: " + panel.getSlider().getValue());
            handler.onBpmChange();
        });

    }
}

