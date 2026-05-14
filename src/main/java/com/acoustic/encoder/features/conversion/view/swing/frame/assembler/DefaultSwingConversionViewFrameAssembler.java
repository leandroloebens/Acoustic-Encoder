package com.acoustic.encoder.features.conversion.view.swing.frame.assembler;

import com.acoustic.encoder.features.conversion.view.swing.components.ParameterComboBoxPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.ParameterSliderPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.TrackSelectorPanel;
import com.acoustic.encoder.features.conversion.view.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.dto.InstrumentOption;
import com.acoustic.encoder.shared.view.swing.components.*;
import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingConversionViewFrameAssembler implements SwingConversionViewFrameAssembler {

    private final static int BORDERLAYOUT_HGAP = 10;
    private final static int BORDERLAYOUT_WGAP = 10;

    private final static int BUTTONS_VGAP = (int) (7 * SwingUtils.getScreenScaleRatio());
    private final static int BUTTONS_HGAP = (int) (7 * SwingUtils.getScreenScaleRatio());

    private final static int PARAMETERS_HGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int PARAMETERS_VGAP = (int) (35 * SwingUtils.getScreenScaleRatio());
    private final static int PARAMETERS_BORDER_PADDING = 10;

    private final static int CONFIG_PANEL_MAX_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width/2;

    private final SwingButton converterButton;
    private final SwingButton saveButton;
    private final SwingButton loadButton;
    private final SwingLabel instructionLabel;
    private final SwingVerticalScrollPane scrollPane;
    private final TrackSelectorPanel trackSelector;
    private final ParameterSliderPanel volumePanel;
    private final ParameterSliderPanel octavePanel;
    private final ParameterComboBoxPanel<InstrumentOption> instrumentPanel;
    private final ParameterSliderPanel bpmPanel;

    public DefaultSwingConversionViewFrameAssembler(ConversionViewSwingComponentsWrapper components) {
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

        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));

        SwingPanel buttonsPanel = createButtonsPanel();
        SwingPanel textAreaPanel = createTextAreaPanel();

        SwingPanel conversionPanel = new SwingPanel(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_WGAP));
        conversionPanel.add(textAreaPanel, BorderLayout.CENTER);
        conversionPanel.add(buttonsPanel, BorderLayout.SOUTH);

        SwingPanel configPanel = createConfigPanel();
        configPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        configPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.setMaximumSize(new Dimension(CONFIG_PANEL_MAX_WIDTH, Integer.MAX_VALUE));

        instrumentPanel.getComboBox().sortItemsAscending();
        SwingPanel configWrapper = new SwingPanel(new GridBagLayout());
        configWrapper.add(configPanel);

        frame.add(conversionPanel, BorderLayout.CENTER);
        frame.add(configWrapper, BorderLayout.NORTH);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setMinimumSize(windowInitialSize);

        return frame;
    }

    @Override
    public ConversionViewSwingComponentsWrapper getComponents() {
        return new ConversionViewSwingComponentsWrapper(
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
        SwingPanel fileButtonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, BUTTONS_HGAP, BUTTONS_VGAP));
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

    private SwingPanel createConfigPanel() {
        SwingPanel configPanel = new SwingPanel();

        configPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, PARAMETERS_HGAP, PARAMETERS_VGAP, PARAMETERS_HGAP);
        gbc.weightx = 1;

        gbc.gridy = 0;
        gbc.gridx = 1;
        configPanel.add(bpmPanel, gbc);

        gbc.insets = new Insets(0, PARAMETERS_HGAP, 0, PARAMETERS_HGAP);
        gbc.gridy++;
        configPanel.add(trackSelector, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        configPanel.add(volumePanel, gbc);

        gbc.gridx++;
        configPanel.add(instrumentPanel, gbc);

        gbc.gridx++;
        configPanel.add(octavePanel, gbc);

        configPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        PARAMETERS_BORDER_PADDING,
                        PARAMETERS_BORDER_PADDING,
                        PARAMETERS_BORDER_PADDING,
                        PARAMETERS_BORDER_PADDING
                )
        );

        return configPanel;
    }

}