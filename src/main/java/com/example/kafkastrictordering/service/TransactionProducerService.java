package com.example.kafkastrictordering.service;

import com.example.kafkastrictordering.event.Transaction;
import com.example.kafkastrictordering.helpers.PartitionSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service

public class TransactionProducerService implements ProducerService<Transaction> {

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Value("${event.topic.transaction}")
    private String TRANSACTION_TOPIC;

    Logger logger = LoggerFactory.getLogger(TransactionProducerService.class);

    public void sendEvent(Transaction event) throws Exception {

        logger.debug("Topic to send: {} ",TRANSACTION_TOPIC);

        Integer partition = PartitionSelector.determineTransactionPartition(event);

        CompletableFuture<SendResult<String, Transaction>> response = kafkaTemplate.send(TRANSACTION_TOPIC, partition, Long.toString(event.getUserId()), event);

        response.whenComplete((result, exception) -> {
            if (exception == null) {
               logger.info(" Message sent to {}, - userId : {} partition : {}, offset : {} ",
                       result.getRecordMetadata().topic(), result.getProducerRecord().key()
                        ,result.getRecordMetadata().partition(),result.getRecordMetadata().offset());
            } else {
                logger.error("Error occurred while sending message : {} with exception : {} "
                        , event, exception.getMessage());
            }
        });
    }
}
