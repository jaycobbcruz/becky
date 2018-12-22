package com.becky.becky.bot.ab.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

public class ApplicationProperties {

	private static final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

	public static final String PROPERTY_FILE = "application.properties";

	private static PropertyResourceBundle resourceBundle;

	static {
		InputStream inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(PROPERTY_FILE);
		try {
			resourceBundle = new PropertyResourceBundle(inputStream);
		} catch (Throwable e) {
			try {
				inputStream = ApplicationProperties.class.getClassLoader().getResourceAsStream(PROPERTY_FILE);
				resourceBundle = new PropertyResourceBundle(inputStream);
			} catch (Throwable t) {
				log.error("ERROR OCCURRED. PLEASE CHECK IF listener.ini or listener.ini EXISTS ON THE CLASSPATH.", t);
			}
		}
	}

	public static String getValue(final String key) {
		try {
			return resourceBundle.getString(key);
		} catch (Throwable e) {
			return key;
		}
	}
}
