package com.acoustic.encoder.features.conversion.ports;

import com.acoustic.encoder.features.conversion.dto.MusicProject;

import java.io.File;
import java.io.IOException;

public interface TextRepository {

    void saveText(String text, File file) throws IOException;

    String loadText(File file) throws IOException, IllegalArgumentException;

    void saveProject(MusicProject input, File file) throws IOException;

    MusicProject loadProject(File file) throws IOException, IllegalArgumentException;
}
