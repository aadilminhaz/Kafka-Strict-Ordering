package com.example.kafkastrictordering.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfigurations {

    @Value("${event.topic.transaction}")
    private String TRANSACTION_TOPIC;


    @Bean
    public NewTopic createTopic() {
        return new NewTopic(TRANSACTION_TOPIC, 3, (short)1);
    }

}
