package com.hj.util;

import java.util.Properties;

public class ConfigUtil {
	private static Properties properties = new Properties();
	static {
		properties.putAll(System.getProperties());
	}

	public static void addProperties(Properties properties) {
		ConfigUtil.properties.putAll(properties);
	}

	public static Properties getProperties() {
		return properties;
	}

}