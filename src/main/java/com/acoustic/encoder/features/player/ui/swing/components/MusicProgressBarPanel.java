package com.acoustic.encoder.features.player.ui.swing.components;

import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingLabel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingPanel;
import com.acoustic.encoder.infrastructure.ui_shared.swing.components.SwingSeekBar;

import javax.swing.event.ChangeListener;
import java.awt.*;

public class MusicProgressBarPanel extends SwingPanel {

    private final SwingSeekBar progressBar;
    private final SwingLabel currentTime;
    private final SwingLabel totalTime;

    public MusicProgressBarPanel(SwingSeekBar progressBar, SwingLabel currentTime, SwingLabel totalTime) {
        if (progressBar == null) throw new IllegalArgumentException("progressBar cannot be null");
        if (currentTime == null) throw new IllegalArgumentException("currentTime cannot be null");
        if (totalTime == null) throw new IllegalArgumentException("durationTime cannot be null");

        super(new BorderLayout());

        this.progressBar = progressBar;
        this.currentTime = currentTime;
        this.totalTime = totalTime;

        initializePanel();
    }

    private void initializePanel() {
        SwingPanel timePanel = new SwingPanel(new BorderLayout());
        timePanel.setOpaque(false);

        timePanel.add(currentTime, BorderLayout.WEST);
        timePanel.add(totalTime, BorderLayout.EAST);

        this.setOpaque(false);

        this.add(progressBar, BorderLayout.CENTER);
        this.add(timePanel, BorderLayout.SOUTH);
    }

    public void setProgressTimeLabel(String currentTime, String totalTime) {
        this.currentTime.setText(currentTime);
        this.totalTime.setText(totalTime);
    }

    public void setValue(int value) {
        progressBar.setValue(value);
    }

    public int getMaximum() {
        return progressBar.getMaximum();
    }

    public boolean getValueIsAdjusting() {
        return progressBar.getValueIsAdjusting();
    }

    public boolean isAvailableForManualChange () {
        return progressBar.hasFocus() && !progressBar.getValueIsAdjusting();
    }

    public void transferFocus() {
        progressBar.transferFocus();
    }

    public long getMicroSecondsPosition(long microSecondsDuration) {
        double percentage = (double) progressBar.getValue() / progressBar.getMaximum();
        return (long) (percentage * microSecondsDuration);
    }

    public void addChangeListener(ChangeListener changeListener) {
        if (changeListener == null) throw new IllegalArgumentException("changeListener cannot be null");
        progressBar.addChangeListener(changeListener);
    }

    public void removeChangeListener(ChangeListener changeListener) {
        if (changeListener == null) throw new IllegalArgumentException("changeListener cannot be null");
        progressBar.removeChangeListener(changeListener);
    }

    public SwingSeekBar getProgressBar() {
        return progressBar;
    }
}