package com.chdor.schema_registry.example.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.everit.json.schema.ArraySchema;
import org.everit.json.schema.ObjectSchema;
import org.everit.json.schema.Schema;
import org.everit.json.schema.StringSchema;
import org.everit.json.schema.loader.SchemaLoader;
//import org.everit.json.schema.loader.SpecificationVersion;
import org.json.JSONObject;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Utils;
import com.chdor.schema_registry.example.json.model.TVSeriesActor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaDraft;
//import com.kjetland.jackson.jsonSchema.JsonSchemaDraft;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import com.kjetland.jackson.jsonSchema.SubclassesResolver;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.entities.SchemaReference;
import io.confluent.kafka.schemaregistry.json.JsonSchema;
import io.confluent.kafka.schemaregistry.json.JsonSchemaProvider;
import io.confluent.kafka.schemaregistry.json.JsonSchemaUtils;
import io.confluent.kafka.schemaregistry.json.SpecificationVersion;
import scala.Option;;

public class JSONSchemaUtils {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JSONSchemaUtils.class);
	
	public static void main(String[] args) throws Exception {

		createSchemaFromScratchWithJsonSchemaValidator();
		createSchemaFromScratchwithJsonNode();
		
		JsonSchema jsonSchema = loadSchema();
		printSchemaInfos(jsonSchema);
		
		updateSchemaWithJSONSchemaValidator();
		updateSchemaWithJsonNode();

		generateSchemaFromPojoWithJacksonJsonSchemaGenerator();
		generateSchemaFromPojoWithConfluentSchemaRegistry();
		
//		updateJSONSchemaWithJacksonJsonSchemaGenerator();
		
//		updateSchema();
//		updateSchema1();
//		updateJSONSchemaFromScratch();

		
		//load
		//print info
		
//		JsonSchema jsonSchema = loadSchema(); 
//		printSchemaInfos(jsonSchema);
//		createJSONSchemaFromScratch();
//		updateSchema();
//		buildsSchemaByEverit_Org();
		
//		// Generate Json Schema from a POJO (Jackson annoted class) with Confluent
//		JsonSchema jsonSchema = createJsonSchemaByConfluent();
//		logger.info("JSON Schema:");
//		logger.info(jsonSchema.canonicalString());
//		System.out.println("Json Schema version: "+jsonSchema.getString("$schema"));
//		System.out.println("Json Schema Type: "+jsonSchema.schemaType());
//		System.out.println("Json Name: "+jsonSchema.name());
//		System.out.println("Schema Title: "+jsonSchema.getString("title"));
//		
//		jsonSchema.validate();
//		
//		JsonNode jsonNode = jsonSchema.toJsonNode();
//		JsonNode properties= jsonNode.get("properties");
//		Iterator<Map.Entry<String, JsonNode>> fields = properties.fields();
//		 while  (fields.hasNext()) {
//			 Map.Entry<String, JsonNode> subFields = fields.next();
//			 System.out.println("key :"+subFields.getKey()+" / "+"value: "+subFields.getValue().toString());
//		 
//			 if ( subFields.getKey().equals("tvShow")) {
//				 JsonNode fieldnode = subFields.getValue();
//				 
//				 Iterator<Map.Entry<String, JsonNode>> fieldnodes = fieldnode.fields();
//				 
//				 ArrayNode arrayNode = fieldnode.withArray("oneOf");
//				 JsonNode typeNode = arrayNode.get(1);
//				String type = typeNode.get("type").asText();
//				
//				((ObjectNode)typeNode).put("type", "int");
//				 
//				System.out.println("schema int: "+jsonNode.toString());
//				 
//				 
//			 }
//		 
//		 }
//		
//		System.out.println("Properties node type: "+properties.getNodeType().name());
//		
//		modifyJsonSchema(jsonSchema);
		
		
//		TVSeriesActor person = new TVSeriesActor().withFirstName("Steve")
//				.withLastName("Austin")
//				//.withTVShow("The Six Million Dollar Man")
//				;
//
//		
//		JsonSchema jsonSchema = JsonSchemaUtils.getSchema(person);
//		logger.info("JSON Schema:");
//		logger.info(jsonSchema.canonicalString());
//		System.out.println("Schema Name: "+jsonSchema.name());
//		System.out.println("Schema Version: "+jsonSchema.version());
//		System.out.println("Schema Title: "+jsonSchema.getString("title"));
//		System.out.println("Schema RAW: "+jsonSchema.rawSchema().toString());
//
//		//
//		JsonSchemaProvider jsonSchemaProvider = new JsonSchemaProvider();
//		List<SchemaReference> references = new ArrayList<>();
//		String jsonSchemaString = Utils.load("json-schema/TVSeriesActor5.json");
//		Optional<ParsedSchema> jsonParsedSchema= jsonSchemaProvider.parseSchema(jsonSchemaString, references);
//		ParsedSchema parsedSchema = jsonParsedSchema.get();
//		JsonSchema jsonSchemaParsed = (JsonSchema)parsedSchema;
//		logger.debug("\nJson TVSeriesActor 5: "+jsonSchemaParsed.toString());
//		
////		JsonNode jsonNode = jsonSchema.toJsonNode();
//		
//		//DefaultGenerationConfig defaultGenerationConfig = new DefaultGenerationConfig();
//		String jsonSchemaFile = "json-schema/TVSeriesActor.json";
//		System.out.println("Schema file: "+getResourceOutputdir(jsonSchemaFile));
//		//CustomGenerationConfig customGenerationConfig = new CustomGenerationConfig("com.chdor.schema_registry.example.json.model");
//		List<String> z = new ArrayList<String>(Arrays.asList("zaza"));
//		
//		CustomGenerationConfig customGenerationConfig = new CustomGenerationConfig()
//				.setSchemaPath(new ArrayList<>(Arrays.asList( getResourceOutputdir(jsonSchemaFile) )))
//				.setTargetPackage("com.chdor.schema_registry.example.json.model")
//				;
//				
//				//"com.chdor.schema_registry.example.json.model");
//		Jsonschema2Pojo jsonschema2Pojo = new Jsonschema2Pojo();
//		Jsonschema2Pojo.generate(customGenerationConfig, null);
//		
//		//JsonSchemaUtils..toObject(null, jsonSchema)
//		String schemaString = SchemasDef.JSON_TVSeriesActor_SCHEMA;
//		logger.info("SchemaString:");
//		logger.info(schemaString);
//		JsonSchema jsonSchema2 = new JsonSchema(SchemasDef.JSON_TVSeriesActor_SCHEMA);
//		logger.info("Confluent JSON Schema: "+jsonSchema2.canonicalString());
//
//		
//		//JsonSchemaUtils.toObject(null, jsonSchema)
//		//AbstractKafkaJsonSchemaDeserializer<T>
//		
//		//JsonSchemaMessageDeserializer q = null;
//		//JsonSchemaMessageFormatter jsonSchemaMessageFormatter = new JsonSchemaMessageFormatter();
//		//jsonSchemaMessageFormatter.
//		
//		//SchemaRegistryClient
		
	}
	
	/**
	 * Load a JSON Schema (get as string)
	 */
	public static JsonSchema loadSchema() {
		String jsonSchemafile = "TVSeriesActor2.json";
		// Create a JsonSchemaProvider
		JsonSchemaProvider jsonSchemaProvider = new JsonSchemaProvider();
		// As the loaded schema has no Schemas references, I create an empty list
		List<SchemaReference> references = new ArrayList<>();
		// Simply load the schema and return it contents as string
		String jsonSchemaString = Utils.load("json-schema/".concat(jsonSchemafile));
		// If the JSON schema is successfully parsedn then it is returned
		Optional<ParsedSchema> jsonParsedSchema= jsonSchemaProvider.parseSchema(jsonSchemaString, references);
		ParsedSchema parsedSchema = jsonParsedSchema.get();
		// Cast the parsedSchema to a JSON Schema
		JsonSchema jsonSchema = (JsonSchema)parsedSchema;
		logger.info("Load JSON Schema: "+jsonSchemafile);
		logger.info("Display Schema:\n"+jsonSchema.toJsonNode().toPrettyString());
		return jsonSchema;
	}
	
	/**
	 * Print some Infos of a JSON Schema
	 * @param jsonSchema
	 */
    public static void printSchemaInfos( JsonSchema jsonSchema ) {
		logger.info("Print JSON Schema Infos");
		logger.info("JSON Schema string: "+jsonSchema.canonicalString());
		logger.info("JSON Raw Schema string: "+jsonSchema.rawSchema().toString());
		logger.info("$schema: "+jsonSchema.getString("$schema"));
		logger.info("name: "+jsonSchema.name());
		logger.info("type: "+jsonSchema.schemaType());
		if ( jsonSchema.version() != null ) {
			logger.info("- version: "+String.valueOf(jsonSchema.version()));
		}
		logger.info("Raw Schema Title: "+jsonSchema.rawSchema().getTitle());
		logger.info("Raw Schema ID: "+jsonSchema.rawSchema().getId());
		logger.info("Raw Schema Description: "+jsonSchema.rawSchema().getDescription());
		if ( jsonSchema.rawSchema().isReadOnly() != null) {
			logger.info("isReadOnly: "+String.valueOf(jsonSchema.rawSchema().isReadOnly()));
		}
		if ( jsonSchema.rawSchema().isWriteOnly() != null) {
			logger.info("isWriteOnly: "+String.valueOf(jsonSchema.rawSchema().isWriteOnly()));
		}
		if ( jsonSchema.rawSchema().isNullable() != null) {
			logger.info("isNullable: "+String.valueOf(jsonSchema.rawSchema().isNullable()));
		}
		
		JsonNode jsonNode = jsonSchema.toJsonNode();
		JsonNode propertiesNode = jsonNode.get("properties");
		Iterator<Entry<String,JsonNode>> propertiesJsonNodes = propertiesNode.fields();
		logger.info("Properties Fields:");
		while ( propertiesJsonNodes.hasNext() ) {
			Entry<String,JsonNode> propertieJsonNode = propertiesJsonNodes.next();
			logger.info(" - Field Name: "+propertieJsonNode.getKey());
			logger.info(" - \""+propertieJsonNode.getKey()+"\""+" Field Type: "+propertieJsonNode.getValue().toString());
		}
    }
	
	/**
	 * JSON Schema Validator
	 * https://github.com/everit-org/json-schema
	 * https://avro.apache.org/docs/current/api/java/org/apache/avro/SchemaBuilder.html
	 * https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/schema/MongoJsonSchema.html
	 * https://stackoverflow.com/questions/38449968/generate-json-schema-manally-using-java-codegson-without-any-pojo
	 * https://github.com/java-json-tools/json-schema-validator/blob/master/src/main/java/com/github/fge/jsonschema/examples/Utils.java
	 */
	public static void createSchemaFromScratchWithJsonSchemaValidator() {

		// Define the $schema draft version
		// I use my own Draft enum
		Map<String,Object> schemaOpts = new HashMap<String,Object>();
		schemaOpts.put("$schema", JsonSchemaDraftVersion.DRAFT_07.url());
		schemaOpts.put("additionalProperties", false);
		
		org.everit.json.schema.Schema jsonSchema = ObjectSchema.builder()
			    .addPropertySchema("firstName", StringSchema.builder().build())
			    .addPropertySchema("lastName", StringSchema.builder().build())
			    .addPropertySchema("tvShow", StringSchema.builder().build())
			    .addPropertySchema("actor", StringSchema.builder().requiresString(true).defaultValue("Michael Landon").build())
			    .addPropertySchema("testArray", ArraySchema.builder().additionalItems(true)
			        .allItemSchema(StringSchema.builder().build())
			        .build())
			    .unprocessedProperties(schemaOpts)
				.id("http://com.chdor.schema_registry.example.model.json")
				.title("TVSeriesActor")
				.description("TV 1970-1980 Series Actors")
			    .build();
		
		//logger.info("Create JSON Schema from scratch with Schema Validator (org.everit): "+jsonSchema.toString());
		logger.info("Create JSON Schema from scratch with Schema Validator:\n"+new JSONObject(jsonSchema.toString()).toString(2));
		
		
		// Load the JSON Schema and specifying the Draft Version
		SchemaLoader loader = SchemaLoader.builder()
                .schemaJson(new JSONObject(jsonSchema.toString()))
                .draftV7Support() // or draftV7Support()
                .build();
 
		jsonSchema = loader.load().build();
	}

	/**
	 * JSON Schema Validator
	 * https://github.com/everit-org/json-schema
	 * https://avro.apache.org/docs/current/api/java/org/apache/avro/SchemaBuilder.html
	 * https://docs.spring.io/spring-data/mongodb/docs/current/api/org/springframework/data/mongodb/core/schema/MongoJsonSchema.html
	 * https://stackoverflow.com/questions/38449968/generate-json-schema-manally-using-java-codegson-without-any-pojo
	 * https://github.com/java-json-tools/json-schema-validator/blob/master/src/main/java/com/github/fge/jsonschema/examples/Utils.java
	 */
	public static void updateSchemaWithJSONSchemaValidator() {
		JsonSchema jsonSchema = loadSchema();
		logger.info("Update Schema - JSON Schema V1:\n"+jsonSchema.toJsonNode().toPrettyString());
		
		JSONObject jsonObject = new JSONObject(jsonSchema.toString());

		Object propJsonObject = jsonObject.get("properties");
		if ( propJsonObject instanceof JSONObject) {
			JSONObject jsonObject2 = (JSONObject)propJsonObject;
			JSONObject tvShowJsonObject = (JSONObject)jsonObject2.get("tvShow");

			// Rebuild the TVShow
			tvShowJsonObject.put("type", "integer");
			tvShowJsonObject.put("default", -1);
			jsonObject2.put("tvShow", tvShowJsonObject);

			// Add a Sypnosis Field
			Map<String,Object> fieldsMap = new HashMap<>(); 
			fieldsMap.put("type", "string");
			fieldsMap.put("default","Unknown");
			jsonObject2.put("Sypnosis", fieldsMap);

			// Add a isAlive new Field
			fieldsMap = new HashMap<>(); 
			fieldsMap.put("type", "boolean");
			fieldsMap.put("default", true);
			jsonObject2.put("IsAlive", fieldsMap);
		}
		
		logger.info("Update Schema - JSON Schema V2:\n"+jsonObject.toString(2));
		
		// Convert the jsonObject to a JSON Schema 
		SchemaLoader loader = SchemaLoader.builder()
                .schemaJson(jsonObject)
                .draftV7Support() // or draftV7Support()
                .build();
		
		Schema schema = loader.load().build();
		jsonSchema = new JsonSchema(schema.toString());
		logger.info("Convert Schema V2 to a JsonSchema and print its again:\n"+ new JSONObject(jsonSchema.toString()).toString(2));
	}

	
	/**
	 * Create a JSON Schema from scratch
	 */
	public static void createSchemaFromScratchwithJsonNode() {
		
		// Define the JSON Draft version 7
		JsonSchemaDraft jsonSchemaDraft = JsonSchemaDraft.DRAFT_07; 
		// Create ObjectMapper
		ObjectMapper mapper = new ObjectMapper();
		
		// If using JsonSchema to generate HTML5 GUI:
	    //JsonSchemaGenerator html5 = new JsonSchemaGenerator(mapper, JsonSchemaConfig.html5EnabledSchema() );

		// Set the JSON Schema draft
		JsonSchemaConfig config = JsonSchemaConfig.vanillaJsonSchemaDraft4().withJsonSchemaDraft(jsonSchemaDraft);
		//JavaType javaType = SimpleType.construct(String.class);
		// Base class for type token classes used both to contain information and as keys for deserializers
		// Define a unknownType
		JavaType javaType = TypeFactory.unknownType();
		// Create the JsonSchemaGenerator (com.kjetland.jackson.jsonSchema.JsonSchemaGenerator)
		// The Schema Draft is trough the config
		JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper,config);
		// Create an initial JSON Schema with "title":"MyJSONSchema" and "description":"An example of JSON Schema created from scratch" 
		JsonNode jsonNode = jsonSchemaGenerator.generateJsonSchema(javaType, "TVSeriesActor", "TV 1970-1980 Series Actors");
		// Add the $id field
		((ObjectNode)jsonNode).put("$id", "http://com.chdor.schema_registry.example.model.json");
		// Add the type field
		((ObjectNode)jsonNode).put("type", "object");
		// Add the additionalProperties field
		((ObjectNode)jsonNode).put("additionalProperties", false);

		// Build Base class that specifies methods for getting access to Node instances (newly constructed, or shared, depending on type), as well as basic implementation of the methods. 
		JsonNodeFactory factory = new JsonNodeFactory(false);
		
		// Create the schema field: {"type":"string"}
		ObjectNode fieldTypeString = factory.objectNode();
		((ObjectNode)fieldTypeString).put("type", "string");

		// Create the schema field: "actor":{"type":"string", "default":"Unknown"}
		ObjectNode actorTypeStringDefault = factory.objectNode();
		((ObjectNode)actorTypeStringDefault).put("type", "string");
		((ObjectNode)actorTypeStringDefault).put("default", "Michael Landon");

		// Create the schema field: {"type":"string", "default":"Unknown"}
		//ObjectNode fieldTypeStringDefault = factory.objectNode();
		//((ObjectNode)fieldTypeStringDefault).put("type", "string");
		//((ObjectNode)fieldTypeStringDefault).put("default", "Unknown");

//		// Create the schema field: "lastName":{"type":"string"}
//		ObjectNode lastName = factory.objectNode();
//		((ObjectNode)lastName).set("lastName", fieldTypeString);
//		
//		// Create the schema field: "lastName":{"type":"string"}
//		ObjectNode firstName = factory.objectNode();
//		((ObjectNode)firstName).set("firstName", fieldTypeString);
		
		// Add fields to the properties element
		Map<String,JsonNode> fieldsMap = new HashMap<>(); 
		fieldsMap.put("lastName", fieldTypeString);
		fieldsMap.put("firstName", fieldTypeString);
		fieldsMap.put("tvShow", fieldTypeString);
		fieldsMap.put("actor", actorTypeStringDefault);
		JsonNode fields = mapper.valueToTree(fieldsMap);
		((ObjectNode)jsonNode).set("properties", fields);

		logger.info("Schema created from scratch with JsonNode & ObjectNode:\n"+jsonNode.toPrettyString());
	}
	
	/**
	 * Generate Json schema from POJO with Jackson jsonSchema Generator
	 * https://github.com/mbknor/mbknor-jackson-jsonSchema
	 */
	public static void generateSchemaFromPojoWithJacksonJsonSchemaGenerator() {
		ObjectMapper mapper = new ObjectMapper();
		// Set the Various parameters
		// Do not enable Title generated
		// By default, without control, the JSON Schema Title name is build using camelcase 
		Boolean autoGenerateTitleForProperties = false;
		Option<String> defaultArrayFormat =null;
		Boolean useOneOfForOption = false;
	    Boolean useOneOfForNullables = false;
	    Boolean usePropertyOrdering = true;
	    Boolean hidePolymorphismTypeProperty = true;
	    Boolean disableWarnings = false;
	    Boolean useMinLengthForNotNull = false;
	    Boolean useTypeIdForDefinitionName = false;
	    // Set to empty map to prevent exception
	    scala.collection.immutable.Map<String, String> customType2FormatMapping = new scala.collection.immutable.HashMap<String, String>();
	    Boolean useMultipleEditorSelectViaProperty = false;
	    scala.collection.immutable.Set<Class<?>> uniqueItemClasses = null;
	    // Set to empty map to prevent exception
	    scala.collection.immutable.Map<Class<?>, Class<?>> classTypeReMapping = new scala.collection.immutable.HashMap<Class<?>, Class<?>>();
	    scala.collection.immutable.Map<String, Supplier<JsonNode>> jsonSuppliers = null;
	    // Set a validated SubclassesResolver to prevent exception
	    //SubclassesResolver subclassesResolver = new SubclassesResolverImpl();
	    SubclassesResolver subclassesResolver = null;
	    // Set "additionalProperties" to false
	    Boolean failOnUnknownProperties = true;
	    // Set to empty Class<?>[] to prevent exception
	    Class<?>[] javaxValidationGroups = new Class<?>[0];
	    
		JsonSchemaDraft jsonSchemaDraft = JsonSchemaDraft .DRAFT_07; 
		JsonSchemaConfig jsonSchemaConfig = new JsonSchemaConfig(autoGenerateTitleForProperties, defaultArrayFormat, useOneOfForOption, useOneOfForNullables, usePropertyOrdering, hidePolymorphismTypeProperty, disableWarnings, useMinLengthForNotNull, useTypeIdForDefinitionName, customType2FormatMapping, useMultipleEditorSelectViaProperty, uniqueItemClasses, classTypeReMapping, jsonSuppliers, subclassesResolver, failOnUnknownProperties, javaxValidationGroups, jsonSchemaDraft);
	    jsonSchemaConfig.withJsonSchemaDraft(jsonSchemaDraft);

		JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper,jsonSchemaConfig);
		String title = "TVSeriesActor";
		String description = "TV Show Actor info";
		//JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(TVSeriesActor.class);
		JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(TVSeriesActor.class,title,description);
		logger.info("JSON Schema generated from POJO with Jackson jsonSchema Generator:\n"+jsonSchema.toPrettyString());
	}
	
	/**
	 * 
	 */
	public static void updateSchemaWithJsonNode() {
		// Load the schema TVSeriesActor1 as schema-V1
		JsonSchema jsonSchema = loadSchema();
		JsonNode schema = jsonSchema.toJsonNode();
		logger.info("Update Schema with JsonNode:");
		logger.info("Schema - V1:\n"+schema.toPrettyString());
		
		JsonNode rootNode = jsonSchema.toJsonNode();
		JsonNode propertiesNode = rootNode.get("properties");
		Iterator<Entry<String,JsonNode>> propertiesJsonNodes = propertiesNode.fields();
		Set<Entry<String,JsonNode>> fieldsSet = new HashSet<>();
		
		//logger.info("Properties Fields:");
		while ( propertiesJsonNodes.hasNext() ) {
			Entry<String,JsonNode> propertieJsonNode = propertiesJsonNodes.next();
			fieldsSet.add(propertieJsonNode);
			if ( propertieJsonNode.getKey().equals("tvShow")) {
				// Define a new type
				JsonNodeFactory factory = new JsonNodeFactory(false);
				ObjectNode fieldTypeInt = factory.objectNode();
				((ObjectNode)fieldTypeInt).put("type", "int");
				((ObjectNode)fieldTypeInt).put("default", -1);
				propertieJsonNode.setValue(fieldTypeInt);
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		
		// Backup the original fields
		Map<String,JsonNode> fieldsMap = new HashMap<>(); 
		Iterator<Entry<String, JsonNode>> iterator = fieldsSet.iterator();
		while ( iterator.hasNext() ) {
			Entry<String, JsonNode> entry = iterator.next();
			fieldsMap.put(entry.getKey(), entry.getValue());
		}

		// Build Base class that specifies methods for getting access to Node instances (newly constructed, or shared, depending on type), as well as basic implementation of the methods. 
		JsonNodeFactory factory = new JsonNodeFactory(false);
		// Build the field "isalive";{"ttpe":"boolean", "default":true}
		ObjectNode fieldTypeBoolDefault = factory.objectNode();
		((ObjectNode)fieldTypeBoolDefault).put("type", "boolean");
		((ObjectNode)fieldTypeBoolDefault).put("default", true);
		fieldsMap.put("isAlive", fieldTypeBoolDefault);
		// Build the field "Synopsis";{"type":"string", "default":Unknown}
		ObjectNode synopsisfieldType = factory.objectNode();
		((ObjectNode)synopsisfieldType).put("type", "string");
		((ObjectNode)synopsisfieldType).put("default", "Unknown");
		fieldsMap.put("Synopsis", synopsisfieldType);
		// Transform map o fields to JsonNode
		JsonNode fields = mapper.valueToTree(fieldsMap);
		((ObjectNode)rootNode).set("properties", fields);
		
		logger.info("Schema - V2:\n"+rootNode.toPrettyString());

		// Convert the jsonObject to a JSON Schema 
		jsonSchema = new JsonSchema(rootNode.toString());
		logger.info("Convert Schema V2 to a JsonSchema and print its again:\n"+ new JSONObject(jsonSchema.toString()).toString(2));
	}
	

	/**
	 * https://github.com/mbknor/mbknor-jackson-jsonSchema
	 */
//	public static void updateJSONSchemaWithJacksonJsonSchemaGenerator() {
//	    // If you want to configure it manually:
//		Boolean autoGenerateTitleForProperties = false;
//		Option<String> defaultArrayFormat =null;
//		Boolean useOneOfForOption = false;
//	    Boolean useOneOfForNullables = false;
//	    Boolean usePropertyOrdering = true;
//	    Boolean hidePolymorphismTypeProperty = true;
//	    Boolean disableWarnings = false;
//	    Boolean useMinLengthForNotNull = false;
//	    Boolean useTypeIdForDefinitionName = false;
//	    scala.collection.immutable.Map<String, String> customType2FormatMapping = null;
//	    Boolean useMultipleEditorSelectViaProperty = false;
//	    scala.collection.immutable.Set<Class<?>> uniqueItemClasses = null;
//	    scala.collection.immutable.Map<Class<?>, Class<?>> classTypeReMapping = null;
//	    scala.collection.immutable.Map<String, Supplier<JsonNode>> jsonSuppliers = null;
//	    SubclassesResolver subclassesResolver = new SubclassesResolverImpl();
//	    Boolean failOnUnknownProperties = false;
//	    Class<?>[] javaxValidationGroups = null;
//	
//	    
//		JsonSchemaDraft jsonSchemaDraft = JsonSchemaDraft .DRAFT_07; 
//	
//		ObjectMapper mapper = new ObjectMapper();
//		// Initialize input parameters
//		JsonSchemaConfig jsonSchemaConfig = new JsonSchemaConfig(autoGenerateTitleForProperties, defaultArrayFormat, useOneOfForOption, useOneOfForNullables, usePropertyOrdering, hidePolymorphismTypeProperty, disableWarnings, useMinLengthForNotNull, useTypeIdForDefinitionName, customType2FormatMapping, useMultipleEditorSelectViaProperty, uniqueItemClasses, classTypeReMapping, jsonSuppliers, subclassesResolver, failOnUnknownProperties, javaxValidationGroups, jsonSchemaDraft);
//		// Set the JSON Schema Version
//	    jsonSchemaConfig.withJsonSchemaDraft(jsonSchemaDraft);
//	
//		// If using JsonSchema to generate HTML5 GUI:
//		//ObjectMapper mapper = new ObjectMapper();
//	    //JsonSchemaGenerator html5 = new JsonSchemaGenerator(mapper, JsonSchemaConfig.html5EnabledSchema() );
//
//		// Set the JSON Schema Version
//		JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper,jsonSchemaConfig);
//		//JavaType javaType = SimpleType.construct(String.class);
//		JavaType javaType = TypeFactory.unknownType();
//		JsonNode jsonNode = jsonSchemaGenerator.generateJsonSchema(javaType, "MyJSONSchema", "An example of JSON Schema created from scratch");
//		((ObjectNode)jsonNode).put("$id", "http://com.chdor.schema_registry.example.model.json");
//		((ObjectNode)jsonNode).put("type", "object");
//		((ObjectNode)jsonNode).put("additionalProperties", false);
//
//		JsonNodeFactory factory = new JsonNodeFactory(false);
//		//TextNode textNode = factory.textNode("tvShow");
//		
//		ObjectNode fieldTypeString = factory.objectNode();
//		((ObjectNode)fieldTypeString).put("type", "string");
//
//		ObjectNode fieldTypeStringDefault = factory.objectNode();
//		((ObjectNode)fieldTypeStringDefault).put("type", "string");
//		((ObjectNode)fieldTypeStringDefault).put("default", "Unknown");
//
//		ObjectNode lastName = factory.objectNode();
//		((ObjectNode)lastName).set("lastName", fieldTypeString);
//		
//		ObjectNode firstName = factory.objectNode();
//		((ObjectNode)firstName).set("firstName", fieldTypeString);
//
//		Map<String,JsonNode> fieldsMap = new HashMap<>(); 
//		fieldsMap.put("lastName", fieldTypeString);
//		fieldsMap.put("firstName", fieldTypeString);
//		fieldsMap.put("tvShow", fieldTypeStringDefault);
//		fieldsMap.put("actor", fieldTypeStringDefault);
//		
//		JsonNode fields = mapper.valueToTree(fieldsMap);
//		
//		((ObjectNode)jsonNode).set("properties", fields);
//		
//		//"properties":{"lastName":{"type":"string"},"firstName":{"type":"string"},"tvShow":{"type":"string","default":"Unknown"}}}
//		
//		
//		logger.info("JsonNode:\n"+jsonNode.toPrettyString());
//
//
//	}

	
	/**
	 * 
	 * @throws Exception
	 */
	public static void generateSchemaFromPojoWithConfluentSchemaRegistry() throws Exception{

		// Populate Json object all fields in order to create a full Json Schema
		
		//SchemaRegistryClient

		JsonSchema jsonSchema = JsonSchemaUtils.getSchema(new TVSeriesActor());
		logger.info("JSON Schema generated from POJO with Confluent Schema Registry:\n"+jsonSchema.toJsonNode().toPrettyString());

		
		CachedSchemaRegistryClient cachedSchemaRegistryClient = new CachedSchemaRegistryClient("http://garlick:8081",
				10);
		
		// Populate Json object all fields in order to create a full Json Schema
		jsonSchema = JsonSchemaUtils.getSchema(new TVSeriesActor(), SpecificationVersion.DRAFT_7, false, cachedSchemaRegistryClient);

		// Populate Json object all fields in order to create a full Json Schema
		logger.info("Disable oneof expression - JSON Schema generated from POJO with Confluent Schema Registry:\n"+jsonSchema.toJsonNode().toPrettyString());

	}
	
	
	public static void modifyJsonSchema(JsonSchema jsonSchema) {
		JsonNode jsonNode = jsonSchema.toJsonNode();
		((ObjectNode)jsonNode).put("title", "TitleModified");

		JsonSchemaProvider jsonSchemaProvider = new JsonSchemaProvider();
		List<SchemaReference> references = new ArrayList<>();
		String jsonSchemaString = jsonNode.toString(); //Utils.load("json-schema/TVSeriesActor5.json");
		Optional<ParsedSchema> jsonParsedSchema= jsonSchemaProvider.parseSchema(jsonSchemaString, references);
		ParsedSchema parsedSchema = jsonParsedSchema.get();
		//JsonSchema jsonSchemaParsed = (JsonSchema)parsedSchema;
		System.out.println("New Schema: "+parsedSchema.toString());
	}
	
}

