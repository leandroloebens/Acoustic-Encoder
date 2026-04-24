package com.acoustic.encoder.features.conversion.service;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class DefaultFIleService implements FileService {

    public DefaultFIleService() {}

    @Override
    public void saveTextToFile(String text, File file) throws IOException {

        if (!file.getName().toLowerCase().endsWith(".txt")) {
            file = new File(file.getParentFile(), file.getName() + ".txt");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }

    }

    @Override
    public String loadTextFromFile(File file) throws IOException {

        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

    }

}
