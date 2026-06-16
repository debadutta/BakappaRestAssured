package com.testautomation.apitesting.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

@Test
public class TestReadProperties {
	@Test
	public void getProperty() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("src/test/resources/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(properties.getProperty("env.baseURI"));
	}

}
