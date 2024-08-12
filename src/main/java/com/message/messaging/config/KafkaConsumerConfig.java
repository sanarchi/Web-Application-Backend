package com.message.messaging.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.message.messaging.entity.Message;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, Message> consumerFactory()
	{
	
	    Map<String, Object> configProps = new HashMap<>();
	    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
	    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "chat");
	    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
	   

	    return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(),
	            new JsonDeserializer<>(Message.class));
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Message> KafkaListenerContainerFactory()
	{
		ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
