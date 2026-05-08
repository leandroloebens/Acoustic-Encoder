package com.acoustic.encoder.features.conversion.config;

import com.acoustic.encoder.features.conversion.dto.MusicParameters;
import com.acoustic.encoder.features.conversion.model.TrackParameters;
import com.acoustic.encoder.shared.view.ViewConfigLoader;

import java.io.*;
import java.util.*;

public class MusicParametersConfigLoader {

    private final static String MISSING_VALUE_ERROR_MSG = "Track missing or incomplete: ";

    private final String fileName;

    public MusicParametersConfigLoader(String fileName) {

        if (fileName == null) throw new IllegalArgumentException("File name cannot be null!");
        this.fileName = fileName;

    }

    public MusicParameters loadDefaultMusicParameters() {
        List<TrackParameters> trackParameters = new ArrayList<>();

        HashMap<String, Integer> map = loadConfigMap();

        int maxTrackIndex = map.get("MAX_TRACK_INDEX");
        map.remove("MAX_TRACK_INDEX");

        for (int i = 0; i <= maxTrackIndex; i++) {
            if (map.get("TRACK_" + i + "_INSTRUMENT") == null
                || map.get("TRACK_" + i + "_VOLUME") == null
                || map.get("TRACK_" + i + "_OCTAVE") == null
            ) {
                System.out.println(MISSING_VALUE_ERROR_MSG + i);
                i++;
            }

            System.out.println("Track " + i + ":");
            trackParameters.add(new TrackParameters(
                    map.get("TRACK_" + i + "_VOLUME"),
                    map.get("TRACK_" + i + "_OCTAVE"),
                    map.get("TRACK_" + i + "_INSTRUMENT")
            ));
        }

        return new MusicParameters(map.get("UNIVERSAL_BPM"), trackParameters);

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
                throw new IOException("Properties file not found: " + fileName);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load properties: " + e.getMessage());
        }

        return constants;

    }

}
