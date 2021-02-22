package com.chdor.schema_registry.example;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * SchemaRegistryByRestTemplate</br>
 * Use the SpringBoot RestTemplate Restful client
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 **/
public class SchemaRegistryByRestTemplate {

	public static void main(String[] args) {

		String schemaRegistryBaseURL = "http://10.207.56.91:8081";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.schemaregistry.v1+json"));

		String restAPIMethod = schemaRegistryBaseURL.concat("/subjects");
		HttpEntity<?> entity = new HttpEntity<>(null, headers);

		try {
			ResponseEntity<List<String>> response = restTemplate.exchange(restAPIMethod, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<String>>() {
					});

			List<String> subjectsName = response.getBody();
			subjectsName.forEach(subjectName -> System.out.println("Subject Name: " + subjectName));

		} catch (HttpClientErrorException httpClientErrorException) {
			System.out.println(httpClientErrorException.getResponseBodyAsString());
		}

	}

}
