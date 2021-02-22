package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.producer.ProducerJSON;


/**
 * Run the JSON Producer
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */
public class RunJsonProducer {

	public static void main(String[] args) {

		ProducerJSON producer = new ProducerJSON();
		producer.produce();

		System.exit(0);
	}

}
