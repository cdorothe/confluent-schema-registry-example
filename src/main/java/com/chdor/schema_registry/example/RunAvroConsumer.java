package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.consumer.ConsumerAvro;

/**
 * Run the AVRO Consumer
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */

public class RunAvroConsumer {

	public static void main(String[] args) {

		ConsumerAvro consumerAVRO = new ConsumerAvro();
		consumerAVRO.consume();
	}

}
