package com.acoustic.encoder.features.start.view.swing.frame;

import com.acoustic.encoder.features.start.controller.StartController;
import com.acoustic.encoder.features.start.view.StartViewManager;
import com.acoustic.encoder.features.start.view.swing.frame.assembler.SwingStartViewFrameAssembler;
import com.acoustic.encoder.features.start.view.swing.frame.binder.SwingStartViewFrameBinder;

public class DefaultStartViewManager implements StartViewManager {

    private final SwingStartViewFrameAssembler assembler;

    private final SwingStartViewFrameBinder binder;

    public DefaultStartViewManager(SwingStartViewFrameAssembler assembler, SwingStartViewFrameBinder binder) {
        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");
        this.assembler = assembler;

        if (binder == null) throw new IllegalArgumentException("Binder cannot be null!");
        this.binder = binder;
    }

    @Override
    public void assemble(StartController controller) {

    }

    @Override
    public void show() {

    }

    public void hide() {

    }

    public void dispose() {

    }

}
