package com.acoustic.encoder.features.player.playback;

public class PlaybackProgressFormatter {
    private static final long MICROSECONDS_PER_SECOND = 1000000;
    private static final String TIME_FORMAT = "%02d:%02d";

    public static int toProgressValue(long currentMicrosec, long totalMicrosec, int sliderMaximum) {

        return (int) Math.round(
                (double) sliderMaximum * currentMicrosec / totalMicrosec
        );
    }

    public static String formatPlaybackTime(long microsec) {
        long totalSeconds = microsec / MICROSECONDS_PER_SECOND;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        return String.format(TIME_FORMAT, minutes, seconds);
    }
}
