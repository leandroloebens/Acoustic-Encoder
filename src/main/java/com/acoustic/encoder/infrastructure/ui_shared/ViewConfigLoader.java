package com.acoustic.encoder.infrastructure.ui_shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class ViewConfigLoader {

    private final String fileName;

    public ViewConfigLoader(String fileName) {

        if (fileName == null) throw new IllegalArgumentException("File name cannot be null!");
        this.fileName = fileName;

    }

    public HashMap<String, String> loadConfigMap() {
        
        HashMap<String, String> constants = new HashMap<>();
        
        try (InputStream input = ViewConfigLoader.class.getResourceAsStream("/" + fileName)) {
            if (input != null) {
                Properties properties = new Properties();
                properties.load(input);
                for (String key : properties.stringPropertyNames()) {
                    constants.put(key, properties.getProperty(key));
                }
            } else {
                throw new IOException("Properties file not found: " + fileName);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load properties: " + e.getMessage());
        }

        return constants;
    }

}