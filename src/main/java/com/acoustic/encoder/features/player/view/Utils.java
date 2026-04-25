package com.acoustic.encoder.features.player.view;

import javax.swing.*;
import java.awt.*;

public final class Utils {
    public static void setHandCursor(AbstractButton... buttons) {
        Cursor hand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        for (AbstractButton b : buttons) {
            b.setCursor(hand);
        }
    }
}
