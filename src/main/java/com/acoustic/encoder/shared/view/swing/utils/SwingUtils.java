package com.acoustic.encoder.shared.view.swing.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class SwingUtils {

    public static final int SAVE_FILE_OPERATION = 0;
    public static final int LOAD_FILE_OPERATION = 1;

    private static final int DEFAULT_FILE_CHOOSER_WIDTH = 600;
    private static final int DEFAULT_FILE_CHOOSER_HEIGHT = 400;
    private static final int DEFAULT_FILE_CHOOSER_FONT_SIZE = 14;

    private static final int DEFAULT_SCREEN_WIDTH = 1920;

    public static File getFileFromChooser(
            int operation,
            Component parent,
            String extensionFilter,
            String filterDescription,
            String dialogTitle
    ) {

        JFileChooser fileChooser = new JFileChooser();

        if (extensionFilter != null) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(filterDescription, extensionFilter);
            fileChooser.setFileFilter(filter);
        }

        fileChooser.setDialogTitle(dialogTitle);

        int width = (int) (DEFAULT_FILE_CHOOSER_WIDTH * getScreenScaleRatio());  // or any ratio you prefer
        int height = (int) (DEFAULT_FILE_CHOOSER_HEIGHT * getScreenScaleRatio());
        fileChooser.setPreferredSize(new Dimension(width, height));

        Font chooserFont =
                new Font(Font.SANS_SERIF, Font.PLAIN, (int)(DEFAULT_FILE_CHOOSER_FONT_SIZE * getScreenScaleRatio()));
        setFileChooserFont(fileChooser, chooserFont);

        int userSelection;

        if (operation == SAVE_FILE_OPERATION)
            userSelection = fileChooser.showSaveDialog(parent);
        else if (operation == LOAD_FILE_OPERATION)
            userSelection = fileChooser.showOpenDialog(parent);
        else
            throw new IllegalArgumentException("Operation must be either SAVE_FILE_OPERATION or LOAD_FILE_OPERATION!");

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            if (extensionFilter != null) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(extensionFilter)) {
                    file = new File(file.getParentFile(), file.getName() + "." + extensionFilter);
                }

                return file;
            }
            else return fileChooser.getSelectedFile();
        }
        else
            return null;
    }

    private static void setFileChooserFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFileChooserFont(child, font);
            }
        }
    }

    public static float getScreenScaleRatio() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (float)(screenSize.getWidth() / DEFAULT_SCREEN_WIDTH);
    }

    public static void setHandCursor(AbstractButton... buttons) {
        Cursor hand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        for (AbstractButton b : buttons) {
            b.setCursor(hand);
        }
    }

}
