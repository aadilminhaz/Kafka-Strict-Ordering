package com.example.kafkastrictordering.contoller;

import com.example.kafkastrictordering.event.Transaction;
import com.example.kafkastrictordering.service.ProducerService;
import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    ProducerService<Transaction> transactionProducerService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @RequestMapping(value={""}, method = RequestMethod.POST)
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            transactionProducerService.sendEvent(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            logger.error("Error in sendEvent eventId: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
