package com.chdor.schema_registry.example.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Config;
import com.chdor.schema_registry.example.avro.model.TVSeriesActor;
import com.chdor.schema_registry.example.avro.model.TVSeriesActorID;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
//import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class ConsumerAvro {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumerAvro.class);
	
	public void consume() {
        logger.info("Start AVRO Consumer");
        Properties props = new Properties();
	    // The Kafka Broker used by the Producer. In case of Kafka cluster, It's a comma list of <FQDN or IP address of Kafka Broker X>:<port>
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
        
        // The Schema Registry Rest API URL
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_URL);

        // This value is mandatory and can take any value you want. If not set Kafka Consumer creation failed.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        
        // Configure the Subject naming strategy
        props.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY, "io.confluent.kafka.serializers.subject.RecordNameStrategy");
        props.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, "io.confluent.kafka.serializers.subject.TopicNameStrategy");
        
        // Enable or disable using the latest Subject schema version 
        //props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true); 

        // Deserializer class for message <key> that implements the <org.apache.kafka.common.serialization.Deserializer> interface.
        // As my message <key> is a String I'm using the StringDeserializer.
        // Several deserializers are provided by default by Kafka. They are located on the package <org.apache.kafka.common.serialization>
        // To list some of ones:
        // ByteArrayDeserializer, BytesDeserializer, DoubleDeserializer, FloatDeserializer, IntegerDeserializer, LongDeserializer and ShortDeserializer 
        // You can create your own Deserializer by implementing the <org.apache.kafka.common.serialization.Serializer> interface.
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());

        // Deserializer class for value that implements the <org.apache.kafka.common.serialization.Deserializer> interface. 
        // As my message <value> is an Avro record I'm using the KafkaAvroDeserializer.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        
        // If true, tries to look up the SpecificRecord class (deserialize TVSeriesActor) 
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        // Create Kafka consumer
        KafkaConsumer<TVSeriesActorID,TVSeriesActor> consumer = new KafkaConsumer<TVSeriesActorID,TVSeriesActor>(props);

        // The Kafka Topic to consume on
        String topic = Config.AVRO_TOPIC;

        // Consumer Topic Subscription 
        consumer.subscribe(Collections.singletonList(topic));

        // Consumer messages 
        try {
			while (true) {
				// Retrieve a message
				ConsumerRecords<TVSeriesActorID, TVSeriesActor> actors = consumer.poll(Duration.ofMillis(100));
				// Display each messages key and value
				for (ConsumerRecord<TVSeriesActorID, TVSeriesActor> actor : actors) {
					TVSeriesActorID key = actor.key();
					TVSeriesActor value = actor.value();
					logger.info("Key: " + key.toString() + " / value: " + value.toString());
				}
		    }

		} finally {
			consumer.close();
		}
	}

}
