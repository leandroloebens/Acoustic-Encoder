package com.acoustic.encoder.features.conversion.ports;

import com.acoustic.encoder.features.conversion.dto.UserConversionInput;

import java.io.File;
import java.io.IOException;

public interface TextRepository {

    void saveText(String text, File file) throws IOException;

    String loadText(File file) throws IOException;

    void saveProject(UserConversionInput input, File file);

    UserConversionInput loadProject(File file);
}
