package com.acoustic.encoder.features.conversion.config;

import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.model.VoiceParameters;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

import java.io.*;
import java.util.*;

public class MusicParametersConfigLoader {

    private final static String PROPERTIES_FILE_NULL_ERROR_MSG = "Properties file cannot be null!";

    private final static String MISSING_VOICE_VALUE_ERROR_MSG = "Voice missing or incomplete: ";
    private final static String MISSING_VALUE_ERROR_MSG = "Missing value: ";

    private final static String PROPERTIES_FILE_NOT_FOUND_ERROR_MSG = "Properties file not found: ";
    private final static String PROPERTIES_FILE_IO_ERROR_MSG = "Properties file IO error: ";

    private final String fileName;

    public MusicParametersConfigLoader(String fileName) {

        if (fileName == null) throw new IllegalArgumentException(PROPERTIES_FILE_NULL_ERROR_MSG);
        this.fileName = fileName;

    }

    public MusicParameters loadDefaultMusicParameters() {
        List<VoiceParameters> voiceParameters = new ArrayList<>();

        HashMap<String, Integer> map = loadConfigMap();

        if (map.get("MAX_VOICE_INDEX") == null)
            throw new IllegalArgumentException(MISSING_VOICE_VALUE_ERROR_MSG + "MAX_VOICE_INDEX");

        int maxTrackIndex = map.get("MAX_VOICE_INDEX");

        for (int i = 0; i <= maxTrackIndex; i++) {
            if (map.get("VOICE_" + i + "_INSTRUMENT") == null
                || map.get("VOICE_" + i + "_VOLUME") == null
                || map.get("VOICE_" + i + "_OCTAVE") == null
            ) {
                throw new IllegalArgumentException(MISSING_VOICE_VALUE_ERROR_MSG + i);
            }

            voiceParameters.add(new VoiceParameters(
                    map.get("VOICE_" + i + "_VOLUME"),
                    map.get("VOICE_" + i + "_OCTAVE"),
                    map.get("VOICE_" + i + "_INSTRUMENT")
            ));
        }

        if (map.get("UNIVERSAL_BPM") == null)
            throw new IllegalArgumentException(MISSING_VALUE_ERROR_MSG + "UNIVERSAL_BPM");

        return new MusicParameters(map.get("UNIVERSAL_BPM"), voiceParameters);

    }

    private HashMap<String, Integer> loadConfigMap() {

        HashMap<String, Integer> constants = new HashMap<>();

        try (InputStream input = ViewConfigLoader.class.getResourceAsStream("/" + fileName)) {
            if (input != null) {
                Properties properties = new Properties();
                properties.load(input);
                for (String key : properties.stringPropertyNames()) {
                    constants.put(key, Integer.parseInt(properties.getProperty(key)));
                }
            } else {
                throw new IOException(PROPERTIES_FILE_NOT_FOUND_ERROR_MSG + fileName);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(PROPERTIES_FILE_IO_ERROR_MSG + e.getMessage());
        }

        return constants;

    }

}
