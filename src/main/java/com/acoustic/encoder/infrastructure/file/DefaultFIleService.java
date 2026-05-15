package com.acoustic.encoder.infrastructure.file;

import com.acoustic.encoder.domain.ports.FileService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class DefaultFIleService implements FileService {

    public DefaultFIleService() {}

    @Override
    public void saveToTextFile(String text, File file) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }

    }

    @Override
    public String loadFromTextFile(File file) throws IOException {

        return Files.readString(file.toPath());

    }

}
