package com.acoustic.encoder.infrastructure.ui_shared.swing.utils;

import java.awt.*;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class SwingFontUtils {

    private static final String NULL_FOLDER_PATH_ERROR_MSG = "Folder path cannot be null";
    private static final String EMPTY_FOLDER_PATH = "Empty fonts folder: ";
    private static final String RESOURCE_FOLDER_NOT_FOUND_ERROR_MSG = "Resource folder not found: ";
    private static final String INVALID_FOLDER_PATH_ERROR_MSG = "Invalid resource folder path: ";
    private static final String FOLDER_WALKING_ERROR_MSG = "Error walking font folder: ";

    private static final String LOADED_FONT_MSG = "Loaded font: ";
    private static final String FAILED_TO_LOAD_FONT_MSG = "Failed to load font from: ";

    public static void printAvailableFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();

        Arrays.stream(fonts)
                .sorted(Comparator.comparing(Font::getFontName, String.CASE_INSENSITIVE_ORDER))
                .forEach(font -> System.out.println(font.getFontName()));
    }

    public static void loadFontsToSystem(String resourceFolderPath) {
        if (resourceFolderPath == null) throw new IllegalArgumentException(NULL_FOLDER_PATH_ERROR_MSG);

        resourceFolderPath = resourceFolderPath.startsWith("/")
                ? resourceFolderPath.substring(1)
                : resourceFolderPath;

        URL resourceUrl = SwingFontUtils.class
                .getClassLoader()
                .getResource(resourceFolderPath);

        if (resourceUrl == null) {
            System.out.println(RESOURCE_FOLDER_NOT_FOUND_ERROR_MSG + resourceFolderPath);
            return;
        }

        Path folderPath;
        try {
            folderPath = Path.of(resourceUrl.toURI());
        } catch (URISyntaxException e) {
            throw new ExceptionInInitializerError(INVALID_FOLDER_PATH_ERROR_MSG + e.getMessage());
        }

        if (!Files.exists(folderPath)) {
            System.out.println(EMPTY_FOLDER_PATH + folderPath);
            return;
        }

        try (Stream<Path> paths = Files.walk(folderPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> {
                        String n = path.getFileName().toString().toLowerCase();
                        return n.endsWith(".ttf") || n.endsWith(".otf") || n.endsWith(".ttc");
                    })
                    .forEach(path -> {
                        try (InputStream is = Files.newInputStream(path)) {
                            Font font = Font.createFont(Font.TRUETYPE_FONT, is);

                            if (!isFontAlreadyRegistered(font))
                                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);

//                            System.out.println(LOADED_FONT_MSG + font.getFontName());
                        } catch (Exception e) {
                            System.err.println(FAILED_TO_LOAD_FONT_MSG + path + ": " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            System.err.println(FOLDER_WALKING_ERROR_MSG + e.getMessage());
        }
    }

    private static boolean isFontAlreadyRegistered(Font candidate) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        String psName = candidate.getPSName();
        String fontName = candidate.getFontName();

        return Arrays.stream(ge.getAllFonts()).anyMatch(existing ->
                existing.getPSName().equalsIgnoreCase(psName)
                        || existing.getFontName().equalsIgnoreCase(fontName)
        );
    }
}
