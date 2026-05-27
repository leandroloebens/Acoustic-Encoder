package com.acoustic.encoder.infrastructure.ui_shared.swing.utils;

import javax.swing.*;
import java.awt.*;

public class SwingMessageUtils {

    public static void showMessage(Component parent, String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = pane.createDialog(parent, "Message");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public static void showWarningMessage(Component parent, String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE);
        JDialog dialog = pane.createDialog(parent, "Warning");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public static void showErrorMessage(Component parent, String message) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = pane.createDialog(parent, "Error");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
