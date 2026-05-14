package com.acoustic.encoder.features.start.view.swing.frame.assembler;

import com.acoustic.encoder.features.start.view.swing.components.dto.StartViewSwingComponentsWrapper;
import com.acoustic.encoder.shared.view.swing.components.SwingButton;
import com.acoustic.encoder.shared.view.swing.components.SwingFrame;
import com.acoustic.encoder.shared.view.swing.components.SwingLabel;

import java.awt.*;

public class DefaultSwingStartViewFrameAssembler implements SwingStartViewFrameAssembler {

    private SwingLabel titleLabel;
    private SwingButton openProjectButton;
    private SwingButton newProjectButton;

    public DefaultSwingStartViewFrameAssembler(StartViewSwingComponentsWrapper components) {
        this.titleLabel = components.titleLabel();
        this.openProjectButton = components.openProjectButton();
        this.newProjectButton = components.newProjectButton();
    }

    @Override
    public SwingFrame assembleFrame(String title, Dimension windowInitialSize, int frameExitOperation) {
        return new SwingFrame();
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
