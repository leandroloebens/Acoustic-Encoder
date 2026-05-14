package com.acoustic.encoder.shared.view.swing;

import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwingViewConfigWrapper {

    private final static String CONFIG_MAP_NULL_ERROR_MSG = "Config map cannot be null";
    private final static String KEY_NULL_ERROR_MSG = "Key cannot be null: ";
    private final static String KEY_NOT_FOUND_ERROR_MSG = "Key not found: ";

    private final HashMap<String, String> configMap;

    public SwingViewConfigWrapper(HashMap<String, String> configMap) {

        if (configMap == null) throw new IllegalArgumentException(CONFIG_MAP_NULL_ERROR_MSG);
        this.configMap = configMap;

    }

    public List<String> getKeys() {
        return new ArrayList<>(configMap.keySet());
    }

    public String getString(String key) {
        if (key == null) throw new IllegalArgumentException(KEY_NULL_ERROR_MSG + key);
        if (!configMap.containsKey(key)) throw new IllegalArgumentException(KEY_NOT_FOUND_ERROR_MSG + key);
        return configMap.get(key);
    }

    public int getInt(String key) {
        if (key == null) throw new IllegalArgumentException(KEY_NULL_ERROR_MSG + key);
        if (!configMap.containsKey(key)) throw new IllegalArgumentException(KEY_NOT_FOUND_ERROR_MSG + key);
        return Integer.parseInt(configMap.get(key));
    }

    public int getScaledInt(String key) {
        return (int)(getFloat(key) * SwingUtils.getScreenScaleRatio());
    }

    public boolean getBoolean(String key) {
        if (key == null) throw new IllegalArgumentException(KEY_NULL_ERROR_MSG + key);
        if (!configMap.containsKey(key)) throw new IllegalArgumentException(KEY_NOT_FOUND_ERROR_MSG + key);
        return Boolean.parseBoolean(configMap.get(key));
    }

    public float getFloat(String key) {
        if (key == null) throw new IllegalArgumentException(KEY_NULL_ERROR_MSG + key);
        if (!configMap.containsKey(key)) throw new IllegalArgumentException(KEY_NOT_FOUND_ERROR_MSG + key);
        return Float.parseFloat(configMap.get(key));
    }

    public float getScaledFloat(String key) {
        return getFloat(key) * SwingUtils.getScreenScaleRatio();
    }

}
