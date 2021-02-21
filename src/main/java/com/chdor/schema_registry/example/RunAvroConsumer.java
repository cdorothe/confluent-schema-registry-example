package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.consumer.ConsumerAvro;

public class RunAvroConsumer {

	public static void main(String[] args) {

		ConsumerAvro consumerAVRO = new ConsumerAvro();
		consumerAVRO.consume();
	}

}
