package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.producer.ProducerJSON;

public class RunJsonProducer {

	public static void main(String[] args) {

		ProducerJSON producer = new ProducerJSON();
		producer.produce();

		System.exit(0);
	}

}
