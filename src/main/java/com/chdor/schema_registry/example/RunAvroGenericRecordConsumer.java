package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.consumer.ConsumerAvroGenericRecord;

/**
 * Run the AVRO Generic Record Consumer
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */
public class RunAvroGenericRecordConsumer {

	public static void main(String[] args) {

		ConsumerAvroGenericRecord consumerAVRO = new ConsumerAvroGenericRecord();
		consumerAVRO.consume();
	}

}
