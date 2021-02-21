package com.chdor.schema_registry.example;

import java.util.Properties;

public class Config {
	
	public static String SCHEMA_REGISTRY_URL = null;
	public static String BOOTSTRAP_SERVERS = null;
	public static String AVRO_TOPIC = null;
	public static String JSON_TOPIC = null;
	public static String BASEDIR = null;
	
	
	static {
		Properties prop = Utils.loadProperties("application.properties");
		
		if (! prop.isEmpty()) {
			SCHEMA_REGISTRY_URL = prop.getProperty("schema-registry-url");
			BOOTSTRAP_SERVERS = prop.getProperty("bootstrap-servers");
			AVRO_TOPIC = prop.getProperty("avro-topic");
			JSON_TOPIC = prop.getProperty("json-topic");
			BASEDIR = prop.getProperty("project-basedir");
		}
	}
}
