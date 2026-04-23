package com.acoustic.encoder.features.conversion.view.swing;

import com.acoustic.encoder.features.conversion.controller.ConversionController;
import com.acoustic.encoder.features.conversion.dto.UserConversionInput;
import com.acoustic.encoder.features.conversion.view.ConversionScreenManager;
import com.acoustic.encoder.shared.model.MusicConfig;
import com.acoustic.encoder.shared.view.swing.SwingFrame;

public class DefaultSwingConversionScreenManager implements ConversionScreenManager {

    private final static String WINDOW_TITLE = "Conversor: Texto para Som";
    private final static int WINDOW_HEIGHT = 400;
    private final static int WINDOW_WIDTH = 500;

    private final SwingConversionScreenAssembler assembler;
    private SwingFrame frame;

    private int defaultVolume;
    private int defaultOctave;
    private int defaultInstrument;
    private int defaultBpm;

    public DefaultSwingConversionScreenManager(SwingConversionScreenAssembler assembler) {

        if (assembler == null) throw new IllegalArgumentException("Assembler cannot be null!");

        this.assembler = assembler;

    }

    @Override
    public void startFrame(ConversionController controller) {
        frame = (SwingFrame) assembler.assemble(
            WINDOW_TITLE,
            WINDOW_WIDTH,
            WINDOW_HEIGHT,
            conversionAction(controller),
            () -> defaultVolume = assembler.getVolumeSliderValue(),
            () -> defaultInstrument = assembler.getInstrumentSliderValue(),
            () -> defaultOctave = assembler.getOctaveSliderValue(),
            () -> defaultBpm = assembler.getBpmSliderValue()
        );
    }

    @Override
    public void showFrame() { frame.setVisible(true); }

    @Override
    public void hideFrame() { frame.setVisible(false); }

    // void destroyFrame();

    @Override
    public void setInitialDefaultParameters(MusicConfig parameters) {
        this.defaultVolume = parameters.defaultVolume();
        this.defaultOctave = parameters.defaultOctave();
        this.defaultInstrument = parameters.defaultMidiInstrument();
        this.defaultBpm = parameters.bpm();
    }

    private Runnable conversionAction(ConversionController controller) {
        Runnable action = () -> {
            controller.handleConvertAction(
                    new UserConversionInput(
                            this.assembler.getInputText(),
                            defaultInstrument,
                            defaultBpm,
                            defaultOctave,
                            defaultVolume
                    )
            );};

        return action;
    }

}
