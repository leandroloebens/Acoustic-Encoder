package com.acoustic.encoder.infrastructure.file;

import com.acoustic.encoder.features.conversion.ports.TextRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileTextRepository implements TextRepository {

    public FileTextRepository() {}

    @Override
    public void saveText(String text, File file) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(text);
        }

    }

    @Override
    public String loadText(File file) throws IOException {

        return Files.readString(file.toPath());

    }

}
