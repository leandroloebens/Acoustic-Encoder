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

    private static final float DEFAULT_SCREEN_HEIGHT = 1080;

    private static final String INVALID_FILE_OPERATION_MSG =
            "Operation must be either SAVE_FILE_OPERATION or LOAD_FILE_OPERATION!";

    public static float getScreenScaleRatio() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (float)(screenSize.getHeight() / DEFAULT_SCREEN_HEIGHT);
    }

    public static void setHandCursor(Component... components) {
        Cursor hand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        for (Component component : components) {
            component.setCursor(hand);
        }
}

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
            throw new IllegalArgumentException(INVALID_FILE_OPERATION_MSG);


        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return getProcessedFile(fileChooser, extensionFilter);
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

    private static File getProcessedFile(JFileChooser fileChooser, String extensionFilter) {
        if (extensionFilter != null) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(extensionFilter)) {
                file = new File(file.getParentFile(), file.getName() + "." + extensionFilter);
            }

            return file;
        }
        else return fileChooser.getSelectedFile();
    }

}
