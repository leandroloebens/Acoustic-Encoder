package com.acoustic.encoder.features.conversion.view.swing.components.assembler;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.*;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingConversionViewAssembler implements SwingConversionViewAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static int BUTTONS_VGAP = 10;

    private final static int PARAMETERS_VGAP = 50;
    private final static int PARAMETERS_BORDER_PADDING = 10;

    private final SwingButton converterButton;
    private final SwingButton saveButton;
    private final SwingButton loadButton;
    private final SwingLabel instructionLabel;
    private final SwingVerticalScrollPane scrollPane;
    private final SwingRadioButtonGroup trackSelector;
    private final ParameterSliderPanel volumePanel;
    private final ParameterSliderPanel octavePanel;
    private final ParameterComboBoxPanel<Integer> instrumentPanel;
    private final ParameterSliderPanel bpmPanel;

    public DefaultSwingConversionViewAssembler(ConversionViewComponentsWrapper components) {
        this.converterButton = components.converterButton();
        this.saveButton = components.saveTextButton();
        this.loadButton = components.loadTextButton();
        this.instructionLabel = components.instructionLabel();
        this.scrollPane = components.scrollPane();
        this.trackSelector = components.trackSelector();
        this.volumePanel = components.volumePanel();
        this.octavePanel = components.octavePanel();
        this.instrumentPanel = components.instrumentPanel();
        this.bpmPanel = components.bpmPanel();
    }

    @Override
    public SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation
    ) {

        SwingFrame frame = new SwingFrame(title, windowInitialSize, frameExitOperation);

        frame.setMinimumSize(windowInitialSize);

        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        SwingPanel buttonsPanel = createButtonsPanel();
        SwingPanel textAreaPanel = createTextAreaPanel();

        SwingUtils.setHandCursor(converterButton, saveButton, loadButton);

        SwingPanel conversionPanel = new SwingPanel(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));
        conversionPanel.add(textAreaPanel, BorderLayout.CENTER);
        conversionPanel.add(buttonsPanel, BorderLayout.SOUTH);

        SwingPanel configPanel = createConfigPanel(buttonsPanel.getHeight());
        configPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        configPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.add(conversionPanel, BorderLayout.CENTER);
        frame.add(configPanel, BorderLayout.EAST);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        frame.pack();

        return frame;
    }

    @Override
    public ConversionViewComponentsWrapper getComponents() {
        return new ConversionViewComponentsWrapper(
                converterButton,
                saveButton,
                loadButton,
                scrollPane,
                instructionLabel,
                trackSelector,
                volumePanel,
                octavePanel,
                instrumentPanel,
                bpmPanel
        );
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

    private SwingPanel createButtonsPanel() {
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

    private SwingPanel createConfigPanel(int bottomPadding) {
        SwingPanel configPanel = new SwingPanel();

        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));

        int heightGap = (int) (PARAMETERS_VGAP * SwingUtils.getScreenScaleRatio());

        configPanel.add(Box.createVerticalGlue());

        configPanel.add(volumePanel);

        configPanel.add(Box.createVerticalStrut(heightGap));

        configPanel.add(octavePanel);

        configPanel.add(Box.createVerticalStrut(heightGap));

        configPanel.add(bpmPanel);

        configPanel.add(Box.createVerticalStrut(heightGap));

        instrumentPanel.getComboBox().sortItemsAscending();
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

}