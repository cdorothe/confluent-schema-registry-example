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
	private static RestService restService = new RestService(new ArrayList<String>(Arrays.asList(com.chdor.schema_registry.example.Config.SCHEMA_REGISTRY_URL)));
	
	public static void main(String[] args) {
		
		try {
			// -----------------------------------
			// List All Subjects
			// -----------------------------------
			//listSubjects();
			
			// Delete all Subjects
			//purgeAll();
			
			// -----------------------------------
			// Delete some Subjects
			// -----------------------------------
			//purgeSubjects(new ArrayList<String>(Arrays.asList("com.chdor.schema_registry.example.avro.model.TVSeriesActorID",
			//		"my-topic-avro-value",
			//		"my-topic-json-key",
			//		"TVSeriesActor"
			//		)));
			
			// -----------------------------------
			// Initialize Schema Registry Example environment 
			// -----------------------------------
			init();
			
			// -----------------------------------
			// Get All Schema Info
			// -----------------------------------
			//getAllSchemaInfo(restService);

			// -----------------------------------
			// Register a Subject Schema
			// -----------------------------------
			//register("MyCustomSubject", "JSON", SchemasDef.JSON_VRAC);
			
			// Test Schema Compatibility
//			String subject = null;
//			String schemaString = null;
//			String schemaType = null;
//
			// -----------------------------------
//			// Test Compatibility AVRO
			// -----------------------------------
//			logger.info("Test compatibility Schemas - AVRO");
//			subject = "my-topic-avro-value";
//			schemaString = SchemasDef.AVRO_TVSeriesActor_SCHEMA_2;
//			schemaType="AVRO";
//			testCompatibility(schemaString, schemaType, subject, restService);
//
//			logger.info("\n\n");
//
			// -----------------------------------
//			// Test Compatibility JSON
			// -----------------------------------
//			logger.info("Test compatibility Schemas - JSON");
//			subject = "TVSeriesActor";
//			schemaString = SchemasDef.JSON_SIMPLETVSERIESACTOR;
//			schemaType="JSON";
//			testCompatibility(schemaString, schemaType, subject, restService);

		} catch (IOException ioException) {
			logger.error(ioException.getMessage());
		} catch (RestClientException restClientException) {
			logger.error(restClientException.getMessage());
		}
	}

	public static void listSubjects() throws IOException,RestClientException {
		// List All subjects registered in SchemaRegistry
		List<String> subjects = restService.getAllSubjects();
		logger.info("Subjects: "+subjects.toString());
	}
	
	
	public static void register(String subject, String schemaType, String schemaString) throws IOException,RestClientException {
		// Register the AVRO schema ID
		List<SchemaReference> schemaReferences = new ArrayList<>() ;
		Integer id = restService.registerSchema(schemaString, schemaType, schemaReferences, subject);
		logger.info("Subject: "+subject + " registered successfully with id: " + id);
	}
	
	public static void init() throws IOException,RestClientException{
		// Define some Variables
		Integer id = null;
		String schemaType = null;
		String schemaString =null;
		String subject = null;
		List<String> subjects = null;
		List<Integer> ids = null;
		List<SchemaReference> references = new ArrayList<SchemaReference>();
		Map<String,String> requestProperties = new HashMap<String, String>();

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

//		// Set my-topic-avro-value Subject FORWARD_TRANSITIVE Compatibility
//		subject = "my-topic-avro-value";
//		ConfigUpdateRequest confiUpdateRequest = restService.updateCompatibility(CompatibilityType.FORWARD_TRANSITIVE.name(), subject);
//		logger.info(subject + " compatibility updated successfully: " + confiUpdateRequest.getCompatibilityLevel());
		
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

	}
	
	public static void getSchemaInfo (String subject, RestService restService) throws IOException,RestClientException {
		List<Integer> versions = restService.getAllVersions(subject);
		for ( Integer version:versions ) {
			Schema schema = restService.getVersion(subject, version);
			logger.info("Schema Info - Subject: \""+subject+"\" - Version: "+version.toString()+"\n- Schema:\n"+schema.toString());
		}
	}


	public static void getAllSchemaInfo (RestService restService) throws IOException,RestClientException {
		List<String> subjects = restService.getAllSubjects();
		List<Integer> versions = null;
		
		if ( subjects !=null && ! subjects.isEmpty() ) {
			for (String subject:subjects ) {
				
				logger.info("\n\nSchema Info");
				logger.info("- Subject: "+subject);

				versions = restService.getAllVersions(subject);
				for ( Integer version:versions ) {
					Schema schema = restService.getVersion(subject, version);
					logger.info("- Schema Version: "+version.toString());
					logger.info("- Schema:\n"+schema.toString());
				}
				
			}
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

	/**
	 * Delete all Schema Registry Subjects
	 * @throws IOException
	 * @throws RestClientException
	 */
	public static void purgeAll() throws IOException,RestClientException {
		//String subject = null; 
		Map<String,String> requestProperties = new HashMap<String, String>();
		List<Integer> ids = null;
		
		List<String> subjects = restService.getAllSubjects();
		if ( subjects!=null && ! subjects.isEmpty() ) {
			for (String subject:subjects ) {

				ids = restService.deleteSubject(requestProperties, subject, false);
				logger.info("Soft delete - Subject: "+subject+", deleted versions: "+ids);
				ids = restService.deleteSubject(requestProperties, subject, true);
				logger.info("Hard delete - Subject: "+subject+", deleted versions: "+ids);
			}
		}
	}

	/**
	 * Delete Schema Registry subjects
	 * @param subjects
	 * : List of Subjects name
	 * @throws IOException
	 * @throws RestClientException
	 */
	public static void purgeSubjects(List<String> subjects) throws IOException,RestClientException {
		Map<String,String> requestProperties = new HashMap<String, String>();
		List<Integer> ids = null;
		
		if ( subjects!=null && ! subjects.isEmpty() ) {
			for (String subject:subjects ) {

				ids = restService.deleteSubject(requestProperties, subject, false);
				logger.info("Soft delete - Subject: "+subject+", deleted versions: "+ids);
				ids = restService.deleteSubject(requestProperties, subject, true);
				logger.info("Hard delete - Subject: "+subject+", deleted versions: "+ids);
			}
		}
	}

	
}


