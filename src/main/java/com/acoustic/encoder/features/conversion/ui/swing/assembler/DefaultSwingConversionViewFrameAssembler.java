
package com.acoustic.encoder.features.conversion.ui.swing.assembler;

import com.acoustic.encoder.features.conversion.ui.swing.components.dto.ConversionViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.*;
import com.acoustic.encoder.infrastructure.ui_shared.swing.utils.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class DefaultSwingConversionViewFrameAssembler implements SwingConversionViewFrameAssembler {

    private static final int BORDERLAYOUT_HGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private static final int BORDERLAYOUT_VGAP = (int) (10 * SwingUtils.getScreenScaleRatio());

    private final static int BUTTONS_PANEL_TGAP = (int) (0 * SwingUtils.getScreenScaleRatio());
    private final static int BUTTONS_PANEL_LGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int BUTTONS_PANEL_BGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int BUTTONS_PANEL_RGAP = (int) (10 * SwingUtils.getScreenScaleRatio());

    private final static int BUTTONS_VGAP = (int) (7 * SwingUtils.getScreenScaleRatio());
    private final static int BUTTONS_HGAP = (int) (7 * SwingUtils.getScreenScaleRatio());

    private final static int PARAMETERS_HGAP = (int) (10 * SwingUtils.getScreenScaleRatio());
    private final static int PARAMETERS_VGAP = (int) (35 * SwingUtils.getScreenScaleRatio());
    private final static int PARAMETERS_BORDER_PADDING = 10;

    private final static int CONFIG_PANEL_MAX_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width/2;

    private final ConversionViewSwingComponentsWrapper components;

    public DefaultSwingConversionViewFrameAssembler(ConversionViewSwingComponentsWrapper components) {
        this.components = Objects.requireNonNull(components, "Conversion components cannot be null");
    }

    @Override
    public SwingFrame assembleFrame(
            String title,
            Dimension windowInitialSize,
            int frameExitOperation
    ) {

        SwingFrame frame = new SwingFrame(title, windowInitialSize, frameExitOperation);

        frame.setLayout(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_VGAP));

        SwingPanel buttonsPanel = createButtonsPanel();

        SwingPanel conversionPanel = new SwingPanel(new BorderLayout(BORDERLAYOUT_HGAP, BORDERLAYOUT_VGAP));
        conversionPanel.add(components.mainTextAreaPanel(), BorderLayout.CENTER);
        conversionPanel.add(buttonsPanel, BorderLayout.SOUTH);

        SwingPanel configPanel = createConfigPanel();
        configPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        configPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.setMaximumSize(new Dimension(CONFIG_PANEL_MAX_WIDTH, Integer.MAX_VALUE));

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
        return this.components.copy();
    }

    private SwingPanel createButtonsPanel() {
        SwingPanel fileButtonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, BUTTONS_HGAP, BUTTONS_VGAP));
        fileButtonsPanel.add(components.loadTextButton());
        fileButtonsPanel.add(components.saveTextButton());
        fileButtonsPanel.add(components.openProjectButton());
        fileButtonsPanel.add(components.saveProjectButton());

        fileButtonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        components.converterButton().setAlignmentX(Component.CENTER_ALIGNMENT);

        SwingPanel buttonsPanel = new SwingPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        buttonsPanel.add(fileButtonsPanel);

        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_VGAP));

        buttonsPanel.add(components.converterButton());

        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_VGAP));

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(
                BUTTONS_PANEL_TGAP,
                BUTTONS_PANEL_LGAP,
                BUTTONS_PANEL_BGAP,
                BUTTONS_PANEL_RGAP
        ));

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
        configPanel.add(components.bpmPanel(), gbc);

        gbc.insets = new Insets(0, PARAMETERS_HGAP, 0, PARAMETERS_HGAP);
        gbc.gridy++;
        configPanel.add(components.voiceSelector(), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        configPanel.add(components.volumePanel(), gbc);

        gbc.gridx++;
        configPanel.add(components.instrumentPanel(), gbc);

        gbc.gridx++;
        configPanel.add(components.octavePanel(), gbc);

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