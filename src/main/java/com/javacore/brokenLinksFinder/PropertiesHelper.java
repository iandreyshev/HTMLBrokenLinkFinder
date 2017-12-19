package com.javacore.brokenLinksFinder;

import com.javacore.brokenLinksFinder.exception.PropsHelperException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class PropertiesHelper {
    private final static String FILE_NOT_FOUND_ERROR = "Properties file \"%s\" not found";
    private final static String IO_ERROR = "Failed to read file \"%s";
    private final Properties properties = new Properties();

    PropertiesHelper(final File propertiesFileName) throws PropsHelperException {
        try {
            properties.load(new FileInputStream(propertiesFileName));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new PropsHelperException(String.format(FILE_NOT_FOUND_ERROR, propertiesFileName));
        } catch (IOException ioException) {
            throw new PropsHelperException(String.format(IO_ERROR, propertiesFileName));
        }
    }

    Integer getInteger(final String key) {
        try {
            return Integer.parseInt(properties.getProperty(key, null));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    Boolean getBoolean(final String key) {
        return Boolean.parseBoolean(properties.getProperty(key, null));
    }
}
