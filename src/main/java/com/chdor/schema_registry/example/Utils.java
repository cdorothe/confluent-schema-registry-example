package com.chdor.schema_registry.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Utils {

	public static String load( String fileName) {
		InputStream is = loadFile(fileName);
		String doc = printInputStream(is);
		
		return doc;
	}
	
	public static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}	
	
	private static InputStream loadFile( String filename ) {

        ClassLoader classLoader = Utils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + filename);
        } else {
            return inputStream;
        }

	}
	
	
	private static String printInputStream(InputStream is) {
		StringBuilder stringBuilder = new StringBuilder();
		
		try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(streamReader)) {

			String line;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line.trim());
			}
			return stringBuilder.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
	
	
	public static Properties loadProperties(String filename) {
		InputStream is = loadFile(filename);
		Properties prop = new Properties();

		try {
			prop.load(is);
			return prop;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	
}
