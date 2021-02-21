package com.chdor.schema_registry.example.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Config;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class ProducerAVROGenericRecord {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ProducerAVROGenericRecord.class);

	public void produce() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_URL);
		props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, false);

		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

		KafkaProducer<Object, Object> producer1 = new KafkaProducer<Object, Object>(props);

		String topic = Config.AVRO_TOPIC;
		String keyPrefix = "person";
		String key = "";
		Integer personIndex = 1;

		String mySchema = "{ \"namespace\": \"com.chdor.schema_registry.example.avro.model\",\"type\": \"record\",\"name\": \"TVSeriesActor\",\"fields\": [{\"name\": \"firstName\",\"type\": \"string\"},{\"name\": \"lastName\",\"type\": \"string\"},{\"name\": \"tvShow\",\"type\": \"string\"}]}";

		List<GenericRecord> genericRecords = new ArrayList<>();

		Schema.Parser parser = new Schema.Parser();
		Schema schema = parser.parse(mySchema);

		GenericRecord avroRecord = new GenericData.Record(schema);
		avroRecord.put("firstName", "Jaimie");
		avroRecord.put("lastName", "Sommers");
		avroRecord.put("tvShow", "Bionic Woman");
		genericRecords.add(avroRecord);

		avroRecord = new GenericData.Record(schema);
		avroRecord.put("firstName", "John");
		avroRecord.put("lastName", "Steed");
		avroRecord.put("tvShow", "Avengers");
		genericRecords.add(avroRecord);

		ProducerRecord<Object, Object> record = null;

		try {
			for (GenericRecord myPerson : genericRecords) {
				key = keyPrefix.concat("-").concat((personIndex++).toString());
				record = new ProducerRecord<Object, Object>(topic, key, myPerson);
				producer1.send(record);
				logger.info("Send : " + key + " / " + myPerson);
			}

		} catch (SerializationException serializationException) {
			logger.error("ERROR !!!");
			serializationException.printStackTrace();
		} finally {
			producer1.flush();
			producer1.close();
		}
	}
}
