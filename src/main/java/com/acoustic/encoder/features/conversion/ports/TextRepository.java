package com.acoustic.encoder.features.conversion.ports;

import java.io.File;
import java.io.IOException;

public interface TextRepository {

    void saveText(String text, File file) throws IOException;

    String loadText(File file) throws IOException;

}
