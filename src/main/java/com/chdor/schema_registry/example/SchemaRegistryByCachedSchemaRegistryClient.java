package com.chdor.schema_registry.example;

import java.io.IOException;
import java.util.Collection;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

/**
 * SchemaRegistryByCachedSchemaRegistryClient
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 **/
public class SchemaRegistryByCachedSchemaRegistryClient {

	public static void main(String[] args) throws IOException, RestClientException {

		// Define the Schema Registry Rest API base URL
		String schemaRegistryBaseURL = "http://10.207.56.91:8081";

		// Build the CachedSchemaRegistryClient
		Integer identityMapCapacity = 10;
		CachedSchemaRegistryClient cachedSchemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryBaseURL,
				identityMapCapacity);

		Collection<String> subjectNames = cachedSchemaRegistryClient.getAllSubjects();
		subjectNames.forEach(subjectName -> System.out.println("Subject Name: " + subjectName));
	}
}
