package com.acoustic.encoder.shared.view.swing;

import com.acoustic.encoder.shared.view.swing.utils.SwingUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwingViewConfigWrapper {

    private final static String CONFIG_MAP_NULL_ERROR_MSG = "Config map cannot be null";
    private final static String KEY_NULL_ERROR_MSG = "Key cannot be null: ";
    private final static String KEY_NOT_FOUND_ERROR_MSG = "Key not found: ";
    private final static String INVALID_DIMENSION_FORMAT_ERROR_MSG = "Invalid dimension format: ";

    private final HashMap<String, String> configMap;

    public SwingViewConfigWrapper(HashMap<String, String> configMap) {

        if (configMap == null) throw new IllegalArgumentException(CONFIG_MAP_NULL_ERROR_MSG);
        this.configMap = configMap;

    }

    public List<String> getKeys() {
        return new ArrayList<>(configMap.keySet());
    }

    public String getString(String key) {
        keyValidation(key);
        return configMap.get(key);
    }

    public int getInt(String key) {
        keyValidation(key);
        return Integer.parseInt(configMap.get(key));
    }

    public int getScaledInt(String key) {
        return (int)(getFloat(key) * SwingUtils.getScreenScaleRatio());
    }

    public boolean getBoolean(String key) {
        keyValidation(key);
        return Boolean.parseBoolean(configMap.get(key));
    }

    public float getFloat(String key) {
        keyValidation(key);
        return Float.parseFloat(configMap.get(key));
    }

    public float getScaledFloat(String key) {
        return getFloat(key) * SwingUtils.getScreenScaleRatio();
    }

    public Dimension getDimension(String key) {
        keyValidation(key);

        String[] values = configMap.get(key).split(",");
        if (values.length != 2)
            throw new IllegalArgumentException(INVALID_DIMENSION_FORMAT_ERROR_MSG + configMap.get(key));

        return new Dimension(Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim()));
    }

    public Dimension getScaledDimension(String key) {
        Dimension dimension = getDimension(key);
        return new Dimension(
                (int)(dimension.width * SwingUtils.getScreenScaleRatio()),
                (int)(dimension.height * SwingUtils.getScreenScaleRatio())
        );
    }

    private void keyValidation(String key) {
        if (key == null) throw new IllegalArgumentException(KEY_NULL_ERROR_MSG + key);
        if (!configMap.containsKey(key)) throw new IllegalArgumentException(KEY_NOT_FOUND_ERROR_MSG + key);
    }

}
