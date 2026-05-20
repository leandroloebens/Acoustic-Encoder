package com.acoustic.encoder.features.start.config;



import com.acoustic.encoder.domain.shared.Bpm;
import com.acoustic.encoder.domain.shared.InstrumentId;
import com.acoustic.encoder.domain.shared.Octave;
import com.acoustic.encoder.domain.shared.Volume;
import com.acoustic.encoder.domain.voice.VoiceConfig;
import com.acoustic.encoder.features.conversion.dto.MusicProject;
import com.acoustic.encoder.infrastructure.ui_shared.config.ViewConfigLoader;

import java.io.*;
import java.util.*;

public class MusicProjectConfigLoader {

    private final static String PROPERTIES_FILE_NULL_ERROR_MSG = "Properties file cannot be null!";

    private final static String MISSING_VOICE_VALUE_ERROR_MSG = "Voice missing or incomplete: ";
    private final static String MISSING_VALUE_ERROR_MSG = "Missing value: ";

    private final static String PROPERTIES_FILE_NOT_FOUND_ERROR_MSG = "Properties file not found: ";
    private final static String PROPERTIES_FILE_IO_ERROR_MSG = "Properties file IO error: ";

    private final String fileName;

    public MusicProjectConfigLoader(String fileName) {

        if (fileName == null) throw new IllegalArgumentException(PROPERTIES_FILE_NULL_ERROR_MSG);
        this.fileName = fileName;

    }

    public MusicProject loadMusicProject() {
        List<VoiceConfig> voices = new ArrayList<>();

        HashMap<String, String> map = loadConfigMap();

        if (map.get("MAX_VOICE_INDEX") == null)
            throw new IllegalArgumentException(MISSING_VOICE_VALUE_ERROR_MSG + "MAX_VOICE_INDEX");

        int maxTrackIndex = Integer.parseInt(map.get("MAX_VOICE_INDEX"));

        for (int i = 0; i <= maxTrackIndex; i++) {
            if (map.get("VOICE_" + i + "_INSTRUMENT") == null
                || map.get("VOICE_" + i + "_OCTAVE") == null
                || map.get("VOICE_" + i + "_VOLUME") == null
            ) {
                throw new IllegalArgumentException(MISSING_VOICE_VALUE_ERROR_MSG + i);
            }

            voices.add(new VoiceConfig(
                    new InstrumentId(Integer.parseInt(map.get("VOICE_" + i + "_INSTRUMENT"))),
                    new Octave(Integer.parseInt(map.get("VOICE_" + i + "_OCTAVE"))),
                    new Volume(Integer.parseInt(map.get("VOICE_" + i + "_VOLUME")))
            ));
        }

        if (map.get("UNIVERSAL_BPM") == null)
            throw new IllegalArgumentException(MISSING_VALUE_ERROR_MSG + "UNIVERSAL_BPM");

        if (map.get("TEXT") == null)
            throw new IllegalArgumentException(MISSING_VALUE_ERROR_MSG + "TEXT");

        return new MusicProject(map.get("TEXT"), new Bpm(Integer.parseInt(map.get("UNIVERSAL_BPM"))), voices);

    }

    private HashMap<String, String> loadConfigMap() {

        HashMap<String, String> constants = new HashMap<>();

        try (InputStream input = ViewConfigLoader.class.getResourceAsStream("/" + fileName)) {
            if (input != null) {
                Properties properties = new Properties();
                properties.load(input);
                for (String key : properties.stringPropertyNames()) {
                    constants.put(key, properties.getProperty(key));
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
