package com.chdor.schema_registry.example.producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;

import com.chdor.schema_registry.example.Config;
import com.chdor.schema_registry.example.SchemaRegistry;
import com.chdor.schema_registry.example.json.model.TVSeriesActor;
import com.chdor.schema_registry.example.json.model.TVSeriesActorID;

import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.schemaregistry.json.SpecificationVersion;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializerConfig;

public class ProducerJSON {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SchemaRegistry.class);
	
	public void produce() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.BOOTSTRAP_SERVERS);
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, Config.SCHEMA_REGISTRY_URL);
        props.put(AbstractKafkaSchemaSerDeConfig.AUTO_REGISTER_SCHEMAS, false); 
        //props.put(AbstractKafkaSchemaSerDeConfig.USE_LATEST_VERSION, true); 
        
        // Configure the Subject naming strategy
        props.put(AbstractKafkaSchemaSerDeConfig.KEY_SUBJECT_NAME_STRATEGY, "io.confluent.kafka.serializers.subject.TopicNameStrategy");
        props.put(AbstractKafkaSchemaSerDeConfig.VALUE_SUBJECT_NAME_STRATEGY, "io.confluent.kafka.serializers.subject.RecordNameStrategy");

        props.put(KafkaJsonSchemaSerializerConfig.ONEOF_FOR_NULLABLES, false);
        props.put(KafkaJsonSchemaSerializerConfig.JSON_INDENT_OUTPUT, true);

        
        System.out.println("draft: "+SpecificationVersion.DRAFT_7.name().toLowerCase(Locale.ROOT));
        props.put(KafkaJsonSchemaSerializerConfig.SCHEMA_SPEC_VERSION, SpecificationVersion.DRAFT_7.name().toLowerCase(Locale.ROOT));
        
        
        // Configure the Subject naming strategy
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.json.KafkaJsonSchemaSerializer");

        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
       
        // 
        KafkaProducer<TVSeriesActorID,TVSeriesActor> producer = new KafkaProducer<TVSeriesActorID,TVSeriesActor>(props);
       
        String topic = Config.JSON_TOPIC;

        TVSeriesActor tvSeriesActor = null;
        List<TVSeriesActor> actors = new ArrayList<TVSeriesActor>();
        tvSeriesActor = new TVSeriesActor()
        		.withFirstName("Dr. Elizabeth")
        		.withLastName("Merrill")  
        		.withTvShow("Man from Atlantis")
        		//.withActor("Belinda Montgomery")
        		;
        actors.add(tvSeriesActor);

        tvSeriesActor = new TVSeriesActor()
        		.withFirstName("Mark")
        		.withLastName("Merrill")  
        		.withTvShow("Man from Atlantis")
        		//.withActor("Patrick Duffy")
        		;
        actors.add(tvSeriesActor);

        tvSeriesActor = new TVSeriesActor()
        		.withLastName("Knight")
        		.withFirstName("Michael")
        		.withTvShow("k2000")
        		//.withActor("David Hasselhoff")
        		;  
        actors.add(tvSeriesActor);

        tvSeriesActor = new TVSeriesActor()
        		.withLastName("Barstow")
        		.withFirstName("Bonnie")
        		.withTvShow("k2000")
        		//.withActor("Patricia McPherson")
        		;
        actors.add(tvSeriesActor);

        
		for (TVSeriesActor actor : actors) {
			try {
				// Random an Actor ID
				Random r = new Random();
        		int low = 100;
        		int high = 200;
        		int result = r.nextInt(high-low) + low;
				TVSeriesActorID tvSeriesActorID = new TVSeriesActorID().withId(result);
				producer.send(new ProducerRecord<TVSeriesActorID,TVSeriesActor>(topic, tvSeriesActorID, actor));
				logger.info("Send : "+tvSeriesActorID+" / "+actor);
			} catch (SerializationException serializationException) {
				serializationException.printStackTrace();
				if (serializationException.getCause() instanceof RestClientException) {
					RestClientException restClientException = (RestClientException) serializationException.getCause();
					logger.error(restClientException.getMessage());
					break;
				}
			}
		}
       
       producer.flush();
       producer.close();
	}
}
