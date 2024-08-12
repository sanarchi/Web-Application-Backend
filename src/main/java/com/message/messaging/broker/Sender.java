package com.message.messaging.broker;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.message.messaging.entity.Message;

@Service
public class Sender {

	private KafkaTemplate<String, Message> kafkaTemplate ;
	
	public Sender(KafkaTemplate<String, Message> kafkaTemplate)
	{
		this.kafkaTemplate=kafkaTemplate;
	}
	
	public void send(String topic, Message message)
	{
		kafkaTemplate.send(topic, message);
	}
}
