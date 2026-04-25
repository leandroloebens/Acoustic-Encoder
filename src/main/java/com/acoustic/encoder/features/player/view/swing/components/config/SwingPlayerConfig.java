package com.acoustic.encoder.features.player.view.swing.components.config;

import java.util.HashMap;

public class SwingPlayerConfig {

    private final HashMap<String, String> configMap;

    public SwingPlayerConfig(HashMap<String, String> configMap) {

        if (configMap == null) throw new IllegalArgumentException("Config map cannot be null!");
        this.configMap = configMap;

    }

    public String getString(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null!");
        if (!configMap.containsKey(key)) throw new IllegalArgumentException("Key not found!");
        return configMap.get(key);
    }
}
