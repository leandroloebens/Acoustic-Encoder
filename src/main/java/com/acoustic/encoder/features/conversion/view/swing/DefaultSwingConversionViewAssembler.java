package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewComponentsWrapper;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterPanel;
import com.acoustic.encoder.shared.view.swing.components.*;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingConversionViewAssembler implements SwingConversionViewAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static int BUTTONS_VGAP = 10;

    private final static int PARAMETERS_VGAP = 55;
    private final static int PARAMETERS_BORDER_PADDING = 20;

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

    public DefaultSwingConversionViewAssembler(ConversionViewComponentsWrapper components) {
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
            Dimension windowInitialSize,
            int frameExitOperation,
            SwingConversionViewActionHandler handler
    ) {

        SwingFrame frame = new SwingFrame(title, windowInitialSize, frameExitOperation);

        frame.setMinimumSize(windowInitialSize);

        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        SwingPanel buttonsPanel = createButtonsPanel(frame, handler);
        SwingPanel textAreaPanel = createTextAreaPanel();

        SwingUtils.setHandCursor(converterButton, saveButton, loadButton);

        SwingPanel conversionPanel = new SwingPanel(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));
        conversionPanel.add(textAreaPanel, BorderLayout.CENTER);
        conversionPanel.add(buttonsPanel, BorderLayout.SOUTH);

        linkSlidersToParameters(handler);

        SwingPanel configPanel = createConfigPanel(buttonsPanel.getHeight());
        configPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        configPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(conversionPanel, BorderLayout.CENTER);
        frame.add(configPanel, BorderLayout.EAST);

        // Centering the frame
        frame.setLocationRelativeTo(null);

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
    public int getVolumeSliderValue() {
        return volumePanel.getSlider().getValue();
    }

    @Override
    public int getInstrumentSliderValue() {
        return instrumentPanel.getSlider().getValue();
    }

    @Override
    public int getOctaveSliderValue() {
        return octavePanel.getSlider().getValue();
    }

    @Override
    public int getBpmSliderValue() {
        return bpmPanel.getSlider().getValue();
    }

    private SwingPanel createTextAreaPanel() {
        SwingPanel textAreaPanel = new SwingPanel();
        textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));

        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        textAreaPanel.add(instructionLabel);
        textAreaPanel.add(Box.createVerticalStrut(10));
        textAreaPanel.add(scrollPane);

        return textAreaPanel;
    }

    private SwingPanel createButtonsPanel(SwingFrame frame, SwingConversionViewActionHandler handler) {
        setButtonsActions(handler, frame);

        SwingPanel fileButtonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        fileButtonsPanel.add(loadButton);
        fileButtonsPanel.add(saveButton);

        fileButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        converterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        SwingPanel buttonsPanel = new SwingPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        buttonsPanel.add(fileButtonsPanel);

        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_VGAP));

        buttonsPanel.add(converterButton);

        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_VGAP));

        return buttonsPanel;
    }

    private void setButtonsActions(SwingConversionViewActionHandler handler, SwingFrame frame) {
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

    private SwingPanel createConfigPanel(int bottomPadding) {
        SwingPanel configPanel = new SwingPanel();

        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));

        configPanel.add(Box.createVerticalGlue());
        configPanel.add(volumePanel);
        configPanel.add(Box.createVerticalStrut(PARAMETERS_VGAP));
        configPanel.add(octavePanel);
        configPanel.add(Box.createVerticalStrut(PARAMETERS_VGAP));
        configPanel.add(bpmPanel);
        configPanel.add(Box.createVerticalStrut(PARAMETERS_VGAP));
        configPanel.add(instrumentPanel);
        configPanel.add(Box.createVerticalStrut(bottomPadding));
        configPanel.add(Box.createVerticalGlue());

        configPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        PARAMETERS_BORDER_PADDING,
                        PARAMETERS_BORDER_PADDING,
                        PARAMETERS_BORDER_PADDING,
                        PARAMETERS_BORDER_PADDING + BORDERLAYOUT_HGAP)
        );

        // Create a wrapper panel with GridBagLayout for centering
        SwingPanel wrapper = new SwingPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        wrapper.add(configPanel, gbc);

        return wrapper;
    }

    private void linkSlidersToParameters(SwingConversionViewActionHandler handler) {

        setSliderAction(volumePanel, handler::onVolumeChange);
        setSliderAction(octavePanel, handler::onOctaveChange);
        setSliderAction(instrumentPanel, handler::onInstrumentChange);
        setSliderAction(bpmPanel, handler::onBpmChange);

    }

    private void setSliderAction(ParameterPanel panel, Runnable action) {
        panel.getSlider().addChangeListener(e -> {

            int value = panel.getSlider().getValue();
            SwingSlider slider = panel.getSlider();

            if (value < slider.getMinToShow()) slider.setValue(slider.getMinToShow());
            else if (value > slider.getMaxToShow()) slider.setValue(slider.getMaxToShow());

            panel.updateLabel();
            action.run();

        });
    }
}