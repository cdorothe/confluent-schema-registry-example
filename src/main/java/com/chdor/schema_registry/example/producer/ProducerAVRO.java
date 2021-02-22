package com.chdor.schema_registry.example.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Config;
import com.chdor.schema_registry.example.avro.model.TVSeriesActor;
import com.chdor.schema_registry.example.avro.model.TVSeriesActorID;

import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

/**
 * ProducerAVRO</br>
 * @author Christophe Dorothé</br>
 * email: kristophe.dorothe@gmail.com</br>
 * Last modified: 2021-02
 *
 **/
public class ProducerAVRO {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ProducerAVRO.class);

	public void produce() {
		Properties props = new Properties();
		// The Kafka Broker used by the Producer. In case of Kafka cluster, It's a comma
		// list of <FQDN or IP address of Kafka Broker X>:<port>
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);

		// The Schema Registry Rest API URL
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_URL);

		// Disable the auto schema registration. If true, the message is always sent
		// because his associoted schema is registred automatically
		props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, false);

		// Enable or Disable lastest schema version using
		// props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true);

		// Configure the Subject naming strategy
		props.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY,
				"io.confluent.kafka.serializers.subject.RecordNameStrategy");
		props.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY,
				"io.confluent.kafka.serializers.subject.TopicNameStrategy");

		// The number of acknowledgments the producer requires the leader to have
		// received before considering a request complete.
		// This controls the durability of records that are sent.
		props.put(ProducerConfig.ACKS_CONFIG, "all");

		// Setting a value greater than zero will cause the client to resend any record
		// whose send fails with a potentially transient error.
		props.put(ProducerConfig.RETRIES_CONFIG, 0);

		// Serializer class for message <key> that implements the
		// <org.apache.kafka.common.serialization.Serializer> interface.
		// As my message <key> is a String I'm using the StringSerializer.
		// Several serializers are provided by default by Kafka. They are located on the
		// package <org.apache.kafka.common.serialization>
		// To list some of ones:
		// ByteArraySerializer, BytesSerializer, DoubleSerializer, FloatSerializer,
		// IntegerSerializer, LongSerializer and ShortSerializer
		// You can create your own serializer by implementing the
		// <org.apache.kafka.common.serialization.Serializer> interface.
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

		// Serializer class for message <value> that implements the
		// <org.apache.kafka.common.serialization.Serializer> interface.
		// As I send Avro messages I choose the specific Kafka Avro Serializer
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

		// Create some Avro TVSeriesActor data
		KafkaProducer<TVSeriesActorID, TVSeriesActor> producer = new KafkaProducer<TVSeriesActorID, TVSeriesActor>(
				props);

		String topic = Config.AVRO_TOPIC;

		TVSeriesActor tvSeriesActor = null;
		List<TVSeriesActor> actors = new ArrayList<TVSeriesActor>();

		tvSeriesActor = TVSeriesActor.newBuilder().setFirstName("Jaimie").setLastName("Sommers")
				.setTvShow("The Bionic Woman")
				//.setActor("Lindsay Wagner")
				.build();
		actors.add(tvSeriesActor);

		tvSeriesActor = TVSeriesActor.newBuilder().setFirstName("John").setLastName("Steed").setTvShow("The Avengers")
				//.setActor("Patrick Macnee")
				.build();
		actors.add(tvSeriesActor);

		tvSeriesActor = TVSeriesActor.newBuilder().setFirstName("Steve").setLastName("Austin")
				.setTvShow("The Six Million Dollar Man")
				//.setActor("Lee Majors")
				.build();
		actors.add(tvSeriesActor);

		tvSeriesActor = TVSeriesActor.newBuilder().setFirstName("Emma").setLastName("Peel").setTvShow("The Avengers")
				//.setActor("Diana Rigg")
				.build();
		actors.add(tvSeriesActor);

		try {
			// Send the messages defined
			for (TVSeriesActor actor : actors) {
				// Build a random Actor ID
				Random r = new Random();
				int low = 100;
				int high = 200;
				int result = r.nextInt(high - low) + low;
				TVSeriesActorID tvSeriesActorID = new TVSeriesActorID(result);
				// Send the Actor
				producer.send(new ProducerRecord<TVSeriesActorID, TVSeriesActor>(topic, tvSeriesActorID, actor));
				logger.info("Send : " + tvSeriesActorID + " / " + actor);
			}

		} catch (SerializationException serializationException) {
			// For simplicity the exception handling just consists logging errors
			serializationException.printStackTrace();
			if (serializationException.getCause() instanceof RestClientException) {
				RestClientException restClientException = (RestClientException) serializationException.getCause();
				logger.error(restClientException.getMessage());
			}
		} finally {
			// Invoking this method makes all buffered records immediately available to send
			// and blocks on the completion of the requests associated with these records.
			producer.flush();
			// Close this producer. This method blocks until all previously sent requests
			// complete.
			producer.close();
		}
	}
}
