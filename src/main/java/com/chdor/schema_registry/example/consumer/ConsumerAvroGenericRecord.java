package com.chdor.schema_registry.example.consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Config;

//import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class ConsumerAvroGenericRecord {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConsumerAvroGenericRecord.class);

    public void consume() {
        logger.info("Start AVRO Consumer - Generic Record");
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_URL);
		// props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());

        String topic = Config.AVRO_TOPIC;

        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<String, GenericRecord>(props);
        consumer.subscribe(Collections.singletonList(topic));

        String personFRSchema = "{ \"namespace\": \"com.chdor.schema_registry.example.avro.model\",\"type\": \"record\",\"name\": \"TVSeriesActor\",\"fields\": [{\"name\": \"Prénom\",\"type\": \"string\"},{\"name\": \"Nom\",\"type\": \"string\"},{\"name\": \"DateNaissance\",\"type\": \"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(personFRSchema);
        GenericRecord avroRecordv2 = new GenericData.Record(schema);

        String firstName = null;
        String lastName = null;

        try {
            while (true) {
                ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(100));
               for (ConsumerRecord<String, GenericRecord> record : records) {
                    String key = record.key();
                    GenericRecord value = record.value();

                    avroRecordv2 = new GenericData.Record(schema);
                    avroRecordv2.put("Prénom", value.get("firstName"));
                    avroRecordv2.put("Nom", value.get("lastName"));

                    if (value.get("firstName") instanceof org.apache.avro.util.Utf8) {
                        org.apache.avro.util.Utf8 utf8 = (org.apache.avro.util.Utf8) value.get("firstName");
                        firstName = utf8.toString();
					}

                    if (value.get("lastName") instanceof org.apache.avro.util.Utf8) {
                        org.apache.avro.util.Utf8 utf8 = (org.apache.avro.util.Utf8) value.get("lastName");
                        lastName = utf8.toString();
                    }
                    
                    if (firstName.equals("Jaimie") && lastName.equals("Sommers")) {
                        avroRecordv2.put("DateNaissance", "1949/06/22");
                    } else if (firstName.equals("John") && lastName.equals("Steed")) {
                        avroRecordv2.put("DateNaissance", "1922/02/06");
                    }
                    
                    logger.info("- Key: " + key + "- value: " + avroRecordv2);
                }
            }

        } finally {
            consumer.close();
        }
    }
}
