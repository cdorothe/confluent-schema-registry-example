package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.producer.ProducerAVROGenericRecord;

/**
 * Run the AVRO Generic Record Producer
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */
public class RunAvroGenericRecordProducer {

	public static void main(String[] args) {

		ProducerAVROGenericRecord producer = new ProducerAVROGenericRecord();
		producer.produce();

		System.exit(0);
	}

}
