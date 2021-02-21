package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.consumer.ConsumerJSON;

public class RunJsonConsumer {

	public static void main(String[] args) {

		ConsumerJSON consumerJSON= new ConsumerJSON();
		consumerJSON.consume();
	}

}
