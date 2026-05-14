package com.acoustic.encoder.shared.service;

import java.io.File;
import java.io.IOException;

public interface FileService {

    void saveToTextFile(String text, File file) throws IOException;

    String loadFromTextFile(File file) throws IOException;

}
