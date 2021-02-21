package com.chdor.schema_registry.example;

import java.io.IOException;

//import static com.chdor.schema_registry.example.SchemasDef.personValidSchema;
//import static com.chdor.schema_registry.example.SchemasDef.personAVROSchemav1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import io.confluent.kafka.schemaregistry.client.rest.RestService;
import io.confluent.kafka.schemaregistry.client.rest.entities.Schema;
import io.confluent.kafka.schemaregistry.client.rest.entities.SchemaReference;
import io.confluent.kafka.schemaregistry.client.rest.entities.requests.ModeGetResponse;
import io.confluent.kafka.schemaregistry.client.rest.entities.requests.RegisterSchemaRequest;
//import io.confluent.kafka.schemaregistry.client.rest.entities.requests.RegisterSchemaRequest;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;;

public class SchemaRegistry {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SchemaRegistry.class);
	
	public static void main(String[] args) {
		
		// Define some Variables
		Integer id = null;
		String schemaType = null;
		String schemaString =null;
		String subject = null;
		List<String> subjects = null;
		List<Integer> ids = null;
		List<SchemaReference> references = new ArrayList<SchemaReference>();
		Map<String,String> requestProperties = new HashMap<String, String>();
		
		// Create RestService
		//String baseURL = "http://faucon-es.forge.intranet:8081";
		String baseURL = com.chdor.schema_registry.example.Config.SCHEMA_REGISTRY_URL;
				
		RestService restService = new RestService(new ArrayList<String>(Arrays.asList(baseURL)));
		
		try {
			//purge(restService);
			
			// List SchemaRegistry supported schema types
			List<String> schemaTypes =restService.getSchemaTypes();
			logger.info("Schema Types: "+schemaTypes.toString());

			// List SchemaRegistry modes
			ModeGetResponse mode = restService.getMode();
			logger.info("Schema Registry Mode: "+mode.getMode());

			// Get SchemaRegistry Compatibility
			// If subject parameter is null then the global compatibility is returned, else the subject compatibility is returned.
			// Note that if no compatibility was set to the subject (default), it raises an exception.
			// String subject = "my-topic-avro-value";
			subject = null;
			 io.confluent.kafka.schemaregistry.client.rest.entities.Config config = restService.getConfig(subject);
			logger.info("Schema Registry compatibility: " + config.getCompatibilityLevel());

			// List All subjects registered in SchemaRegistry
			subjects = restService.getAllSubjects();
			logger.info("Subjects: "+subjects.toString());

			// Register the AVRO schema ID
			subject = "com.chdor.schema_registry.example.avro.model.TVSeriesActorID";
			schemaType = "AVRO";
			schemaString =SchemasDef.AVRO_TVSeriesActorID_SCHEMA;
			id = restService.registerSchema(schemaString, schemaType, references, subject);
			logger.info("Subject: "+subject + " registered successfully with id: " + id);
			
			// Register the AVRO schema V1
			subject = "my-topic-avro-value";
			schemaType = "AVRO";
			schemaString =SchemasDef.AVRO_TVSeriesActor_SCHEMA_1;
			id = restService.registerSchema(schemaString, schemaType, references, subject);
			logger.info("Subject: "+subject + " registered successfully with id: " + id);

			// Register the JSON schema ID
			subject = "my-topic-json-key";
			schemaType = "JSON";
			schemaString =SchemasDef.JSON_TVSeriesActorID_SCHEMA;
			id = restService.registerSchema(schemaString, schemaType, references, subject);
			logger.info("Subject: "+subject + " registered successfully with id: " + id);

			// Register the JSON schema V1
			subject = "TVSeriesActor";
			schemaType = "JSON";
			schemaString =SchemasDef.JSON_TVSeriesActor_SCHEMA_1;
			id = restService.registerSchema(schemaString, schemaType, references, subject);
			logger.info("Subject: "+subject + " registered successfully with id: " + id);

			// Register A Test JSON schema
			subject = "my-schema-test";
			schemaType = "AVRO";
			schemaString =SchemasDef.AVRO_SIMPLETVSERIESACTOR;
			id = restService.registerSchema(schemaString, schemaType, references, subject);
			logger.info("Subject: "+subject + " registered successfully with id: " + id);
			
			// List All subjects registered in SchemaRegistry
			subjects = restService.getAllSubjects();
			logger.info("Subjects: "+subjects.toString());

//			// Set my-topic-avro-value Subject FORWARD_TRANSITIVE Compatibility
//			subject = "my-topic-avro-value";
//			ConfigUpdateRequest confiUpdateRequest = restService.updateCompatibility(CompatibilityType.FORWARD_TRANSITIVE.name(), subject);
//			logger.info(subject + " compatibility updated successfully: " + confiUpdateRequest.getCompatibilityLevel());
			
			// Delete the my-test Subject
			// Need to do at first a soft delete then an hard delete
			subject = "my-schema-test";
			ids = restService.deleteSubject(requestProperties, subject, false);
			logger.info("Soft delete for the subject: "+subject+", deleted versions: "+ids);
			ids = restService.deleteSubject(requestProperties, subject, true);
			logger.info("Hard delete for the subject: "+subject+", deleted versions: "+ids);

			// List All subjects registered in SchemaRegistry
			subjects = restService.getAllSubjects();
			logger.info("Subjects: "+subjects.toString());

			// Get All Schema Info
			subject = "TVSeriesActor";
			getSchemaInfo(subject, restService);

			// Test Compatibility
			subject = "TVSeriesActor";
			schemaString = SchemasDef.JSON_TVSeriesActor_SCHEMA_2;
			schemaType="JSON";
			//testCompatibility(schemaString, schemaType, subject, restService);
			

		} catch (IOException ioException) {
			logger.error(ioException.getMessage());
		} catch (RestClientException restClientException) {
			logger.error(restClientException.getMessage());
		}
	}

	public static void getSchemaInfo (String subject, RestService restService) throws IOException,RestClientException {
		List<Integer> versions = restService.getAllVersions(subject);
		for ( Integer version:versions ) {
			Schema schema = restService.getVersion(subject, version);
			logger.info("Schema Info - Subject: \""+subject+"\" - Version: "+version.toString()+"\n- Schema:\n"+schema.toString());
		}
	}

	
	public static void testCompatibility(String schemaString, String schemaType, String subject, RestService restService) throws IOException,RestClientException {
		
		logger.info("Schemas Test Compatibility");
		logger.info("- Schema String: "+schemaString);
		logger.info("- Schema Type: "+schemaType);
		logger.info("- Subject: "+subject);
		
		List<Integer> versions = restService.getAllVersions(subject);
		RegisterSchemaRequest registerSchemaRequest = null;
		List<String> compatibilities = null;
		
				
		logger.info("- Versions: "+versions);
		
		if ( versions != null && ! versions.isEmpty() ) {
			for ( Integer version:versions ) {
				Schema schema = restService.getVersion(subject, version);
				logger.info("Schema Info - Subject: \""+subject+"\" - Version: "+version.toString()+"\n-Schema:\n"+schema.toString());
				
				registerSchemaRequest = new RegisterSchemaRequest();
				registerSchemaRequest.setId(schema.getId());
				registerSchemaRequest.setReferences(new ArrayList<SchemaReference>());
				registerSchemaRequest.setSchema(schemaString);
				registerSchemaRequest.setSchemaType(schemaType);
				registerSchemaRequest.setVersion(schema.getVersion());

				compatibilities = restService.testCompatibility(registerSchemaRequest, subject, String.valueOf(schema.getVersion()), true);
				logger.info("Compatibility: "+compatibilities+"\n");
////			List<String> compatibilities = restService.testCompatibility(schemaString, subject, String.valueOf(lastVersion));
//			// You can pass the version as Integer or as String "latest" to get the last registered schema under the specified subject
////			List<String> compatibilities = restService.testCompatibility(schemaString, subject, "latest");
////			List<String> compatibilities = restService.testCompatibility(registerSchemaRequest, subject, "latest", false);
//			
//			List<String> compatibilities = restService.testCompatibility(registerSchemaRequest, subject, String.valueOf(lastVersion), true);

				
				
			}
		}
	}
	
	
	public static void purge(RestService restService) throws IOException,RestClientException {
		String subject = null; 
		Map<String,String> requestProperties = new HashMap<String, String>();
		List<Integer> ids = null;
		
		// Purge the Schemas
		// Need to do at first a soft delete then an hard delete
		subject = "com.chdor.schema_registry.example.avro.model.TVSeriesActorID";
		ids = restService.deleteSubject(requestProperties, subject, false);
		logger.info("Soft delete for the subject: "+subject+", deleted versions: "+ids);
		ids = restService.deleteSubject(requestProperties, subject, true);
		logger.info("Hard delete for the subject: "+subject+", deleted versions: "+ids);

		subject = "my-topic-avro-value";
		ids = restService.deleteSubject(requestProperties, subject, false);
		logger.info("Soft delete for the subject: "+subject+", deleted versions: "+ids);
		ids = restService.deleteSubject(requestProperties, subject, true);
		logger.info("Hard delete for the subject: "+subject+", deleted versions: "+ids);

		subject = "my-topic-json-key";
		ids = restService.deleteSubject(requestProperties, subject, false);
		logger.info("Soft delete for the subject: "+subject+", deleted versions: "+ids);
		ids = restService.deleteSubject(requestProperties, subject, true);
		logger.info("Hard delete for the subject: "+subject+", deleted versions: "+ids);

		subject = "TVSeriesActor";
		ids = restService.deleteSubject(requestProperties, subject, false);
		logger.info("Soft delete for the subject: "+subject+", deleted versions: "+ids);
		ids = restService.deleteSubject(requestProperties, subject, true);
		logger.info("Hard delete for the subject: "+subject+", deleted versions: "+ids);

	}
	
}


