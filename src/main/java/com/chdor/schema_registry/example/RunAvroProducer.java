package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.producer.ProducerAVRO;

/**
 * Run the AVRO Producer
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */
public class RunAvroProducer {

	public static void main(String[] args) {

		ProducerAVRO producer = new ProducerAVRO();
		producer.produce();

		System.exit(0);
	}

}
