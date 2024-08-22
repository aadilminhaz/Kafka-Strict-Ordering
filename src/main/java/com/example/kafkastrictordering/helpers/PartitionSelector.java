package com.example.kafkastrictordering.helpers;

import com.example.kafkastrictordering.constant.AppConstants;
import com.example.kafkastrictordering.event.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartitionSelector {

    static Logger logger = LoggerFactory.getLogger(PartitionSelector.class);

    /**
     *
     * @param Transaction
     * @return Integer - partitionNumber based on the logic,
     * below logic is for the demonstration purpose, for actually business
     * requirement the logic should be designed properly
     */
    public static Integer determineTransactionPartition(Transaction transaction) {
        logger.debug("Partition Selector - selecting partition for eventId : {}", transaction.getEventId());

        if (transaction.getUserId() <= 3000) {
            return AppConstants.THRESHOLD_TRANSACTION_PARTITION;
        }

        if (transaction.getUserId()<=30000) {
            return 0;
        } else {
            return 1;
        }
    }
}
