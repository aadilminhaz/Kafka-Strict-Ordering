package com.example.kafkastrictordering.service;

import com.example.kafkastrictordering.event.Transaction;
import com.example.kafkastrictordering.event.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class StrictOrderingTest {

    private static final String KAFKA_SOURCE = "confluentinc/cp-kafka:latest";

    //Initialise KafkaContainer to create Kafka environment for the tests
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse(KAFKA_SOURCE));

    @DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap.servers", kafka::getBootstrapServers);
    }

    @Autowired
    private TransactionProducerService producerService;


    @Test
    public void sendEvent_TestStringOrdering() throws Exception {
        Transaction transaction = null;

        /*Sending events to Partition-2 -  for Billionaire Shipping*/
        for (int i=1; i<5; i++) {
            if (i % 2 == 0) {
                transaction = new Transaction(10, 1000.0 * i, TransactionType.DEBIT);
            } else {
                transaction = new Transaction(10, 1000.0 * i, TransactionType.CREDIT);
            }
            transaction.setEventId((long) i);
            producerService.sendEvent(transaction);
        }

        /*Sending events to Partition-0 - Handled by Normal Shipping*/
        for (int j=100; j<103; j++) {
            transaction = new Transaction(4000,2000.0,  TransactionType.DEBIT);
            transaction.setEventId((long) j);
            producerService.sendEvent(transaction);

        }

        /*Sending events to Partition-1 - Handled by Normal Shipping*/
        for (int k=200; k<203; k++) {
            transaction = new Transaction(60000+k,1000.0,  TransactionType.DEBIT);
            transaction.setEventId((long) k);
            producerService.sendEvent(transaction);

        }
    }
}
