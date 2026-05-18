package com.acoustic.encoder.features.conversion.parser.config;

import com.acoustic.encoder.domain.music.MusicalCommand;
import com.acoustic.encoder.domain.music.MusicalInstruction;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DefaultParsingConfigLoader {

    public final static String DEFAULT_ENCODER_MAPPING_PATH = "encoderMapping/defaultEncoderMapping.properties";

    private final String fileName;

    public DefaultParsingConfigLoader(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, MusicalInstruction> loadConfigMap() throws FileNotFoundException {

        InputStream stream = DefaultParsingConfigLoader.class
                .getClassLoader()
                .getResourceAsStream(this.fileName);

        if (stream == null) throw new FileNotFoundException("File " + this.fileName + " not found");

        return createConfigMap(stream);
    }

    private Map<String, MusicalInstruction> createConfigMap(InputStream stream) {

        Map<String, MusicalInstruction> configMap = new HashMap<>();

        try (stream) {

            Properties prop = new Properties();
            prop.load(stream);

            //  Char -> MusicalInstruction structure in .properties
            prop.forEach((key, instruction) -> {

                //  Parse COMMAND:PARAMETER structure (MusicalInstruction)
                String[] parts = instruction.toString().split(":");
                String commandStr = parts[0];
                // Deal with parameterless instructions (parameter = 0 by default)
                int parameter = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;

                configMap.put(
                        key.toString(),
                        new MusicalInstruction(
                                MusicalCommand.valueOf(commandStr),
                                parameter
                        )
                );
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties");
        }

        return configMap;
    }

}
