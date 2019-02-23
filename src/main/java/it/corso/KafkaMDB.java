package it.corso;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import fish.payara.cloud.connectors.kafka.api.KafkaListener;
import fish.payara.cloud.connectors.kafka.api.OnRecord;

@MessageDriven(activationConfig = {
	    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "testClient"),
	    @ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "test-consumer-group"),
	    @ActivationConfigProperty(propertyName = "topics", propertyValue = "first_topic"),
	    @ActivationConfigProperty(propertyName = "bootstrapServersConfig", propertyValue = "localhost:9092"),   
	    @ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"),   
	    @ActivationConfigProperty(propertyName = "retryBackoff", propertyValue = "1000"),   
	    @ActivationConfigProperty(propertyName = "keyDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),   
	    @ActivationConfigProperty(propertyName = "valueDeserializer", propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"),   
	    @ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000"),   
	})
	public class KafkaMDB implements KafkaListener {
	     
	    @OnRecord( topics={"first_topic"})
	    public void getMessageTest(ConsumerRecord record) {
	        System.out.println("Got record on topic testing " + record);
	    }
	}