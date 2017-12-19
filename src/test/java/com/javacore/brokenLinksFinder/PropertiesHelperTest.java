package com.javacore.brokenLinksFinder;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class PropertiesHelperTest {
	private static final String PROPERTIES_NOT_EXISTING_FILE_NAME = "not_existing_file";
	private static final String PROPERTIES_FILE_NAME = "config";
	private static final String THREADS_COUNT_KEY = "threadsCount";
	private static final Integer THREADS_COUNT = 10;
	private static final String IS_IT_KEY = "itIt";
	private static final Boolean IS_IT = true;

	private PropertiesHelper propertiesHelper;

	@Before
	public void setUp() throws IOException {
		propertiesHelper = new PropertiesHelper(PROPERTIES_FILE_NAME);
	}

	@Test
	public void constructor() {
		try {
			PropertiesHelper propertiesHelper = new PropertiesHelper(PROPERTIES_NOT_EXISTING_FILE_NAME);
		} catch (FileNotFoundException fileNotFoundException) {
			assertTrue(true);
		} catch (IOException ioException) {
			fail();
		}
	}

	@Test
	public void getInteger() {
		assertEquals(THREADS_COUNT, propertiesHelper.getInteger(THREADS_COUNT_KEY));
	}

	@Test
	public void getBoolean() {
		assertEquals(IS_IT, propertiesHelper.getBoolean(IS_IT_KEY));
	}
}
