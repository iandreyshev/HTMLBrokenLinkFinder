package com.javacore.brokenLinksFinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
	private final static String FILE_NOT_FOUND_ERROR = "Конфигурационный файл \"%s\" не найден";
	private final static String IO_ERROR = "Не удаётся прочитать файл \"%s";
	private final Properties properties = new Properties();

	PropertiesHelper(final File propertiesFileName) throws IOException {
		try {
			properties.load(new FileInputStream(propertiesFileName));
		} catch (FileNotFoundException fileNotFoundException) {
			throw new FileNotFoundException(String.format(FILE_NOT_FOUND_ERROR, propertiesFileName));
		} catch (IOException ioException) {
			throw new IOException(String.format(IO_ERROR, propertiesFileName));
		}
	}

	public Integer getInteger(final String key) {
		try {
			return Integer.parseInt(properties.getProperty(key, null));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public Boolean getBoolean(final String key) {
		return Boolean.parseBoolean(properties.getProperty(key, null));
	}
}
