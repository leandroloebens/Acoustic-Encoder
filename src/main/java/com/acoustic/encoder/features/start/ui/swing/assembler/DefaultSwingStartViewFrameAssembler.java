package com.acoustic.encoder.features.start.ui.swing.assembler;

import com.acoustic.encoder.features.start.ui.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingButton;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingFrame;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;

import javax.swing.*;
import java.awt.*;

public class DefaultSwingStartViewFrameAssembler implements SwingStartViewFrameAssembler {

    private final static int BUTTONS_HGAP = 10;
    private final static int BUTTONS_VGAP = 10;

    private final SwingLabel titleLabel;
    private final SwingButton openProjectButton;
    private final SwingButton newProjectButton;

    public DefaultSwingStartViewFrameAssembler(StartViewSwingComponentsWrapper components) {
        this.titleLabel = components.titleLabel();
        this.openProjectButton = components.openProjectButton();
        this.newProjectButton = components.newProjectButton();
    }

    @Override
    public SwingFrame assembleFrame(String title, Dimension windowInitialSize, int frameExitOperation) {
        SwingFrame frame = new SwingFrame(title, windowInitialSize, frameExitOperation);

        SwingPanel buttonsPanel = new SwingPanel(new FlowLayout(FlowLayout.CENTER, BUTTONS_HGAP, BUTTONS_VGAP));
        buttonsPanel.add(newProjectButton);
        buttonsPanel.add(openProjectButton);

        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        SwingPanel mainPanel = new SwingPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(titleLabel);
        mainPanel.add(buttonsPanel);
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        SwingPanel wrapper = new SwingPanel(new GridBagLayout());
        wrapper.add(mainPanel);

        frame.setLayout(new BorderLayout(0, 0));
        frame.add(wrapper, BorderLayout.CENTER);

        // Centering the frame
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setMinimumSize(windowInitialSize);

        return frame;
    }

    @Override
    public StartViewSwingComponentsWrapper getComponents() {
        return new StartViewSwingComponentsWrapper(
                titleLabel,
                openProjectButton,
                newProjectButton
        );
    }

}
