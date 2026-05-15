package com.acoustic.encoder.shared.service;

import com.acoustic.encoder.shared.dto.MusicProject;

import java.io.File;

public interface FileService {

    void saveToTextFile(String text, File file);

    String loadFromTextFile(File file);

    void saveProjectFile(MusicProject input, File file);

    MusicProject loadProjectFile(File file);

}
