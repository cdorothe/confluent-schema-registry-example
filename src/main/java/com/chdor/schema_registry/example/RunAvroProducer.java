package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.producer.ProducerAVRO;

public class RunAvroProducer {

	public static void main(String[] args) {

		ProducerAVRO producer = new ProducerAVRO();
		producer.produce();

		System.exit(0);
	}

}
