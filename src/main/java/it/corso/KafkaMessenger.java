package it.corso;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import javax.resource.ConnectionFactoryDefinition;
import javax.resource.spi.TransactionSupport;

import org.apache.kafka.clients.producer.ProducerRecord;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;

@ConnectionFactoryDefinition(name = "java:module/env/KafkaConnectionFactory", 
			description = "Kafka Connection Factory", 
			interfaceName = "fish.payara.cloud.connectors.kafka.KafkaConnectionFactory", 
			resourceAdapter = "kafka-rar-0.1.0", 
			minPoolSize = 2, maxPoolSize = 2, 
			transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction, 
			properties = { "bootstrapServersConfig=localhost:9092", "clientId=PayaraMicroMessenger" })
@Stateless
public class KafkaMessenger {

	@Resource(lookup = "java:module/env/KafkaConnectionFactory")
	KafkaConnectionFactory factory;

	@Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
	public void sendMessage() {
		try (KafkaConnection conn = factory.createConnection()) {
			conn.send(new ProducerRecord("first_topic", "Sent from Payara Micro."));
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}