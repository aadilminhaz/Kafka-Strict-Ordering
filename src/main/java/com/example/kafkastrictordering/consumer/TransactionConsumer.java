package com.example.kafkastrictordering.consumer;

import com.example.kafkastrictordering.event.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    Logger logger = LoggerFactory.getLogger(TransactionConsumer.class);

    @Value("${event.topic.transaction}")
    public String TRANSACTION_TOPIC;

    @KafkaListener(id="NormalShippingConsumer",
            topics = "${event.topic.transaction}", groupId = "${event.consumer.transaction.groupId.normal}",
            topicPartitions = {@TopicPartition(topic="${event.topic.transaction}", partitions = {"0","1"})})
    public void processTransaction_1(Transaction transaction) {
        /*Write your awesome business logic here*/
        logger.info("Transaction received, NORMAL SHIPPING: {} ",transaction);
    }

    @KafkaListener(id="SpecialShippingConsumer",
            topics = "${event.topic.transaction}", groupId = "${event.consumer.transaction.groupId.normal}",
            topicPartitions = {@TopicPartition(topic="${event.topic.transaction}", partitions = {"2"})})
    public void processTransaction_0(Transaction transaction) {
        /*Write your awesome business logic here*/
        /*Transaction of a billionaire - Ship the product by a chopper*/
        logger.info("Transaction received from partition-2, SHIPPING BY CHOPPER : {} ",transaction);
    }


}
