package com.javacore.brokenLinksFinder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
	private final static String FILE_NOT_FOUND_ERROR = "Конфигурационный файл \"%s\" не найден";
	private final static String IO_ERROR = "Не удаётся прочитать файл \"%s";
	private final Properties properties = new Properties();

	PropertiesHelper(final String propertiesFileName) throws IOException {
		try {
			properties.load(new FileInputStream(propertiesFileName));
		} catch (FileNotFoundException fileNotFoundException) {
			throw new IllegalArgumentException(String.format(FILE_NOT_FOUND_ERROR, propertiesFileName));
		} catch (IOException ioException) {
			throw new IOException(String.format(IO_ERROR, propertiesFileName));
		}
	}

	public Integer getInteger(final String key) {
		try {
			return Integer.parseInt(properties.getProperty(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
