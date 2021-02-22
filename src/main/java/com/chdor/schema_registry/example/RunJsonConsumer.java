package com.chdor.schema_registry.example;

import com.chdor.schema_registry.example.consumer.ConsumerJSON;


/**
 * Run the JSON Consumer
 * 
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 */
public class RunJsonConsumer {

	public static void main(String[] args) {

		ConsumerJSON consumerJSON = new ConsumerJSON();
		consumerJSON.consume();
	}

}
