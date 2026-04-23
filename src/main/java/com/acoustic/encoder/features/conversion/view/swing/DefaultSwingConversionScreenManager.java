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
        frame = assembler.assemble(
            WINDOW_TITLE,
            WINDOW_WIDTH,
            WINDOW_HEIGHT,
            new EventHandler(controller)
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

    private class EventHandler implements SwingEventHandler {
        private final ConversionController controller;

        public EventHandler(ConversionController controller) {
            this.controller = controller;
        }

        @Override
        public void onConvert() {
            controller.handleConvertAction(
                    new UserConversionInput(
                            assembler.getInputText(),
                            defaultInstrument,
                            defaultBpm,
                            defaultOctave,
                            defaultVolume
                    ));
        }

        @Override
        public void onVolumeChange() {
            defaultVolume = assembler.getVolumeSliderValue();
        }

        @Override
        public void onOctaveChange() {
            defaultOctave = assembler.getOctaveSliderValue();
        }

        @Override
        public void onBpmChange() {
            defaultBpm = assembler.getBpmSliderValue();
        }

        @Override
        public void onInstrumentChange() {
            defaultInstrument = assembler.getInstrumentSliderValue();
        }
    }

}
