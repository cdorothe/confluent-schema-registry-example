package com.chdor.schema_registry.example;

import org.apache.kafka.common.serialization.Serializer;

import io.confluent.kafka.serializers.json.AbstractKafkaJsonSchemaSerializer;

public class CustomJSONSerializer<T> extends AbstractKafkaJsonSchemaSerializer<T> implements Serializer<T> {

	@Override
	public byte[] serialize(String topic, T data) {
		// TODO Auto-generated method stub
		return null;
	}

}
