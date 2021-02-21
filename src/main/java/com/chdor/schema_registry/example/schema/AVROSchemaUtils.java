package com.chdor.schema_registry.example.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.reflect.ReflectData;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Utils;
import com.chdor.schema_registry.example.avro.model.TVSeriesActor;
import com.chdor.schema_registry.example.avro.model.TVSeriesActorAVROPojo;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaProvider;
import io.confluent.kafka.schemaregistry.avro.AvroSchemaUtils;
import io.confluent.kafka.schemaregistry.client.rest.entities.SchemaReference;

public class AVROSchemaUtils {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AVROSchemaUtils.class);
	
	public static void main(String[] args) {

		createSchemaFromScratch();
		updateSchema();

		AvroSchema avroSchema = loadSchema();
		printSchemaInfo(avroSchema);
		
		generateSchemaFromPOJOWithAVRO();
		generateSchemaFromPOJOWithConfluent();
		
		createAVRORecordFromGeneratedClasses();
		createAVRORecordFromSchema();

		getSchemaFromAVROGeneratedClasses();
	}
	
	/**
	 * Create an AVRO Schema from Scratch
	 * Use Apache AVRO SchemaBuilder
	 */
	public static void createSchemaFromScratch() {
		Schema schema = SchemaBuilder
	            .record("TVSérieActeur").namespace("com.chdor.schema_registry.example.avro.model")
	            .doc("Fiche descriptive d'un acteur de série des année 1970-1980")
	            .fields()
	            .name("Prénom").type().stringType().noDefault()
	            .name("Nom").type().stringType().noDefault()
	            .name("SérieTV").type().stringType().stringDefault("unknown")
	            .name("Acteur").type().stringType().noDefault()
	            .endRecord();
	
		logger.info("AVRO Schema created from scratch: \n"+schema.toString(true));
	}

	/**
	 * Update a Schema:
	 * Change a Field type and add two new Fields with an optional one
	 */
	public static void updateSchema() {
		AvroSchema avroSchema = loadSchema();
		// Display the Schema version 1 (orignal one)
		logger.info("Original AVRO Schema - V1:\n"+avroSchema.rawSchema().toString(true));

		// Get all Schema V1 fields and re-create them as to be insert in the new Schema
		ArrayList<Schema.Field> listOfNewFields = new ArrayList<>();
		List<Schema.Field> listOfSchemaFields = avroSchema.rawSchema().getFields(); //.getFields();
		Schema.Field newField = null;
	    Schema schemaType = Schema.create(Schema.Type.INT);

		for(Schema.Field field : listOfSchemaFields) {
		    
			// change the "SérieTV" field: type: string->int. Add a doc to explain why its changed 
		    // Add a note to explain why
		    if ( field.name().equals("tvShow" )) {
			       newField = new Schema.Field(field.name(), schemaType, "Type changed: string->int. It's now an index to the TVSeries table");
		    } else {
			       newField = new Schema.Field(field.name(), field.schema(), field.doc());
		    }
		       listOfNewFields.add(newField);
	    }

		// Build a new empty AVRO Schema
	    // Note: We can't modify the original one because when a schema is created, it is locked
	    Schema schemav2 = Schema.createRecord("TVSeriesActor", "TV 1970-1980 Series Actors - V2", "com.chdor.schema_registry.example.avro.model", false);

	    // Create an optional string field: synopsis
	    // It's a union of schema.null and schema.type
	    ArrayList<Schema> optionalString = new ArrayList<Schema>(Arrays.asList(Schema.create(Schema.Type.NULL), Schema.create(Schema.Type.STRING) ));
	    newField = new Schema.Field( "Synopsis", Schema.createUnion(optionalString));
        listOfNewFields.add(newField);
	    
	    // Create an optional int field (it's a union of schema.type and schema.null): Age
        newField = new Schema.Field( "IsAlive", Schema.create(Schema.Type.BOOLEAN),"Indicate if the actor is alive or died",true);
        listOfNewFields.add(newField);

	    // Add the new fields to the schema V2
	    schemav2.setFields(listOfNewFields);

	    // Print the updated Schema
	    logger.info("Updated Schema - V2:\n"+schemav2.toString(true));
	}

	
	/**
	 * Load an AVRO Schema
	 * Use Confluent AvroSchemaProvider
	 */
	public static AvroSchema loadSchema() {
		// Set the AVRO Schema to be loaded
		String avroSchemaFile= "TVSeriesActor1.avsc";
		
		// Build an AvroSchemaProvider
		AvroSchemaProvider avroSchemaProvider = new AvroSchemaProvider();
		// As the schema has no Schemas References, set an empty list
		List<SchemaReference> references = new ArrayList<>();
		// Simply load the AVRO Schema TVSeriesActor4.avsc and return contents as string
		String avroSchemaString = Utils.load("avro-schema/".concat(avroSchemaFile));
		// If the schema is successfully parsed, return an AVRO Schema Instance 
		Optional<ParsedSchema> avroParsedSchema= avroSchemaProvider.parseSchema(avroSchemaString, references);
		// Retrieve the effective schema
		ParsedSchema parsedSchema = avroParsedSchema.get();
		// Get the AVRO Schema
		AvroSchema avroSchema = (AvroSchema)parsedSchema;
		// Display the Schema
		logger.info("Load AVRO Schema: "+avroSchemaFile);
		logger.info("Display Schema:\n"+avroSchema.rawSchema().toString(true));
		return avroSchema;
	}

	
	/**
	 * Prints some AVRO Schema infos
	 */
	public static void printSchemaInfo( AvroSchema avroSchema) {
		logger.info("Display some Schema Infos:");
		// Display the Schema Name
		logger.info("- Schema Name: "+avroSchema.name());
		// Display the Schema Type
		logger.info("- Schema Type: "+avroSchema.schemaType());
		// Display the Schema Doc
		logger.info("- Schema Doc: "+avroSchema.rawSchema().getDoc());
		// Display the Raw Schema Name
		logger.info("- Schema Raw Name: "+avroSchema.rawSchema().getName());
		// Display the Schema Full Name
		logger.info("- Schema Full Name: "+avroSchema.rawSchema().getFullName());
		// Display the Schema Namespace
		logger.info("- Schema Namespace: "+avroSchema.rawSchema().getNamespace());
		List<Field> fields = avroSchema.rawSchema().getFields();
		if ( fields !=null && ! fields.isEmpty() ) {
			// Display the Schema Fields Infos
			logger.info("- Schema Fields:");
			for ( Field field: fields ) {
				logger.info("  - Field name: "+field.name());
				logger.info("  - Field position: "+field.pos());
				logger.info("  - Field schema: "+field.schema().toString(true));
			}
		}
	}

	/**
	 * Generate an AVRO Schema from a POJO AVRO annotations class with AVRO 
	 */
	public static void generateSchemaFromPOJOWithAVRO() {
		// Generates AVRO Schema from POJO TVSeriesActor AVro annoted class
		Schema schema = ReflectData.get().getSchema(TVSeriesActorAVROPojo.class);
		logger.info("AVRO Schema generated from POJO with AVRO API:\n"+schema.toString(true));
	}

	/**
	 * Generate an AVRO Schema from a POJO with Confluent 
	 */
	public static void generateSchemaFromPOJOWithConfluent() {
		TVSeriesActorAVROPojo tvSeriesActorAVROPojo = new TVSeriesActorAVROPojo()
				.setFirstName("Jaimie")
				.setLastName("Summers");
				
		Schema schema = AvroSchemaUtils.getSchema(tvSeriesActorAVROPojo,true);
		logger.info("AVRO Schema generated from POJO with Confluent Schema Registry API (AvroSchemaUtils):\n"+schema.toString(true));
		
		AvroSchema avroSchema = new AvroSchema(AvroSchemaUtils.getSchema(tvSeriesActorAVROPojo, true));
		logger.info("AVRO Schema generated from from POJO with Confluent AvroSchema API:\n"+avroSchema.rawSchema().toString(true));
	}

	/**
	 * Create an AVRO Record from the generated classes
	 */
	public static void createAVRORecordFromGeneratedClasses() {
        TVSeriesActor tvSeriesActor = TVSeriesActor.newBuilder()
        		.setFirstName("Jaimie")
        		.setLastName("Sommers")
        		.build();

        logger.info("- AVRO record created: "+tvSeriesActor.toString());
	}

	
	/**
	 * Create an AVRO record based on Schema 
	 */
	public static void createAVRORecordFromSchema() {
		String mySchema = "{\"type\":\"record\",\"name\":\"TVSeriesActor\",\"namespace\":\"com.chdor.schema_registry.example.avro.model\",\"fields\":[{\"name\":\"firstName\",\"type\":\"string\"},{\"name\":\"lastName\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(mySchema);
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("firstName", "Jaimie");
        avroRecord.put("lastName", "Sommers");
        
        logger.info("Dynamic AVRO record created. Record: "+avroRecord.toString()+".\nSchema used:\n"+schema.toString(true));
	}


	/**
	 * For Just Do It
	 * Retrieve the AVRO Schema from the generated classes 
	 */
	public static void getSchemaFromAVROGeneratedClasses() {
		TVSeriesActor tvSeriesActor =  new TVSeriesActor(); 
		String schemaAvrov = tvSeriesActor.getSchema().toString();
		logger.info("Retrieve AVRO Schema from generated AVRO Java Classes: "+schemaAvrov);
	}
	
}
