package com.acoustic.encoder.shared.view.swing;

import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import java.util.HashMap;

public class SwingViewConfigWrapper {

    private final HashMap<String, String> configMap;

    public SwingViewConfigWrapper(HashMap<String, String> configMap) {

        if (configMap == null) throw new IllegalArgumentException("Config map cannot be null!");
        this.configMap = configMap;

    }

    public String getString(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null!");
        if (!configMap.containsKey(key)) throw new IllegalArgumentException("Key not found!");
        return configMap.get(key);
    }

    public int getInt(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null!");
        if (!configMap.containsKey(key)) throw new IllegalArgumentException("Key not found!");
        return Integer.parseInt(configMap.get(key));
    }

    public int getScaledInt(String key) {
        return (int)(getFloat(key) * SwingUtils.getScreenScaleRatio());
    }

    public boolean getBoolean(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null!");
        if (!configMap.containsKey(key)) throw new IllegalArgumentException("Key not found!");
        return Boolean.parseBoolean(configMap.get(key));
    }

    public float getFloat(String key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null!");
        if (!configMap.containsKey(key)) throw new IllegalArgumentException("Key not found!");
        return Float.parseFloat(configMap.get(key));
    }

    public float getTweakedFloat(String key) {
        return getFloat(key) * SwingUtils.getScreenScaleRatio();
    }

}
