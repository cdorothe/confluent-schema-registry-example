package com.chdor.schema_registry.example;

/**
 * SchemasDef</br>
 * List of all Schemas used
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 **/
public class SchemasDef {

	public static final String AVRO_TVSeriesActor_SCHEMA_1 = Utils.load("avro-schema/TVSeriesActor1.avsc");
	public static final String AVRO_TVSeriesActor_SCHEMA_2 = Utils.load("avro-schema/TVSeriesActor2.avsc");
	public static final String AVRO_TVSeriesActorID_SCHEMA = Utils.load("avro-schema/TVSeriesActorID.avsc");

	public static final String JSON_TVSeriesActor_SCHEMA_1 = Utils.load("json-schema/TVSeriesActor1.json");
	public static final String JSON_TVSeriesActor_SCHEMA_2 = Utils.load("json-schema/TVSeriesActor2.json");
	public static final String JSON_TVSeriesActorID_SCHEMA = Utils.load("json-schema/TVSeriesActorID.json");

	public static final String JSON_SIMPLETVSERIESACTOR = Utils.load("json-schema/SimpleTVSeriesActor.json");
	public static final String AVRO_SIMPLETVSERIESACTOR = Utils.load("avro-schema/SimpleTVSeriesActor.avsc");
}
