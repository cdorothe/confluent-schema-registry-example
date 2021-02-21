package com.chdor.schema_registry.example.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Config;
import com.chdor.schema_registry.example.json.model.TVSeriesActor;
import com.chdor.schema_registry.example.json.model.TVSeriesActorID;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;

public class ConsumerJSON {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumerJSON.class);

	public void consume() {
		logger.info("Start JSON Consumer");
		Properties props = new Properties();

		// Configure Broker Kafka & Schema Registry API
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group8");
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_URL);

		// Configure the Subject naming strategy
		props.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY,
				"io.confluent.kafka.serializers.subject.TopicNameStrategy");
		props.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY,
				"io.confluent.kafka.serializers.subject.RecordNameStrategy");

		// Configure the Deserializers for message Key & Value
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

		// Configure the JSON Object returned by message Key & Value
		props.put(KafkaJsonDeserializerConfig.JSON_KEY_TYPE, TVSeriesActorID.class.getName());
		props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, TVSeriesActor.class.getName());

		String topic = Config.JSON_TOPIC;

		KafkaConsumer<TVSeriesActorID, TVSeriesActor> kafkaConsumer = new KafkaConsumer<TVSeriesActorID, TVSeriesActor>(
				props);
		kafkaConsumer.subscribe(Arrays.asList(topic));

		// ConsumerRecords<String, Person> records = null;

		try {
			while (true) {
				ConsumerRecords<TVSeriesActorID, TVSeriesActor> records = kafkaConsumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<TVSeriesActorID, TVSeriesActor> actor : records) {
					TVSeriesActorID key = actor.key();
					TVSeriesActor value = actor.value();
					logger.info("\n- Key:\n" + key + "\n- value:\n" + value.toString());
				}

			}
		} finally {
			kafkaConsumer.close();
		}

	}
}
