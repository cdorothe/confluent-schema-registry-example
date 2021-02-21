package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.producer.ProducerAVROGenericRecord;

public class RunAvroGenericRecordProducer {

	public static void main(String[] args) {

		ProducerAVROGenericRecord producer = new ProducerAVROGenericRecord();
		producer.produce();
		
		System.exit(0);
	}

}
