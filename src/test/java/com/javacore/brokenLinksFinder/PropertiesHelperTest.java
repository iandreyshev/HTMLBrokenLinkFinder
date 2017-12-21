package com.javacore.brokenLinksFinder;

import com.javacore.brokenLinksFinder.exception.PropsHelperException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class PropertiesHelperTest {
	private static final String PROPERTIES_NOT_EXISTING_FILE_NAME = "not_existing_file";
	private static final String PROPERTIES_FILE_NAME = "config.properties";
	private static final String THREADS_COUNT_KEY = "threadsCount";
	private static final String INVALID_INTEGER_KEY = "invalidInteger";
	private static final Integer THREADS_COUNT = 10;
	private static final String IS_IT_KEY = "isIt";
	private static final Boolean IS_IT = true;

	private PropertiesHelper propertiesHelper;

	@Before
	public void setUp() throws PropsHelperException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(PROPERTIES_FILE_NAME).getFile());
		propertiesHelper = new PropertiesHelper(file);
	}

	@Test (expected = PropsHelperException.class)
	public void constructor() throws PropsHelperException {
		new PropertiesHelper(new File(PROPERTIES_NOT_EXISTING_FILE_NAME));
	}

	@Test
	public void getInteger() {
		assertEquals(THREADS_COUNT, propertiesHelper.getInteger(THREADS_COUNT_KEY));
	}

	@Test
	public void getBoolean() {
		assertEquals(IS_IT, propertiesHelper.getBoolean(IS_IT_KEY));
	}

	@Test
	public void getIntegerReturnNullIfCanNotParseProperty() {
		assertEquals(null, propertiesHelper.getInteger(INVALID_INTEGER_KEY));
	}
}
