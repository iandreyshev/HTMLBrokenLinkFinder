package com.javacore.brokenLinksFinder;

import java.io.FileInputStream;
import java.util.Properties;

class PropertiesParser {
    private Properties properties = new Properties();

    boolean load(final String file) {
        try {
            properties.load(new FileInputStream(file));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    boolean isContainsKey(final String key) {
        return properties.containsKey(key);
    }

    String get(final String key) {
        return properties.getProperty(key);
    }
}
