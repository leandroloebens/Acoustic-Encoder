package com.acoustic.encoder.infrastructure.ui_shared.swing.handler;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSlider;

import javax.swing.event.ChangeListener;
import java.util.List;

public class SliderChangeBindingHandler implements BindingHandler {

    private final SwingSlider slider;
    private final Runnable action;

    public SliderChangeBindingHandler(SwingSlider slider, Runnable action) {
        if (slider == null) throw new IllegalArgumentException("Slider cannot be null");
        this.slider = slider;

        if (action == null) throw new IllegalArgumentException("Action cannot be null");
        this.action = action;
    }

    @Override
    public void bind(List<Runnable> removers) {
        ChangeListener listener = _ -> action.run();
        slider.addChangeListener(listener);
        removers.add(() -> slider.removeChangeListener(listener));
    }

}
