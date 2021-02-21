package com.chdor.schema_registry.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

public class SchemaRegistryByRestService {

	public static void main(String[] args) throws IOException, RestClientException {

		// Define the Schema Registry Rest API base URL
		String schemaRegistryBaseURL = "http://10.207.56.91:8081";

		// Build the RestService
		RestService restService = new RestService(new ArrayList<String>(Arrays.asList(schemaRegistryBaseURL)));

		// Retrieve and Display all Subjects name registered in Schema Registry
		List<String> subjectsName = restService.getAllSubjects();
		subjectsName.forEach(subjectName -> System.out.println("Subject Name: " + subjectName));
	}
}
