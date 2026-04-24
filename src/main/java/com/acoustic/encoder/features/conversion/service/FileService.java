package com.acoustic.encoder.features.conversion.service;

import java.io.File;
import java.io.IOException;

public interface FileService {

    void saveTextToFile(String text, File file) throws IOException;

    String loadTextFromFile(File file) throws IOException;

}
