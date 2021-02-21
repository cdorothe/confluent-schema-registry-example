package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.consumer.ConsumerAvroGenericRecord;

public class RunAvroGenericRecordConsumer {

	public static void main(String[] args) {

		ConsumerAvroGenericRecord consumerAVRO= new ConsumerAvroGenericRecord();
		consumerAVRO.consume();
	}

}
