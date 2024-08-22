package com.example.kafkastrictordering.event;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction extends Event {

    private long userId;
    private Double amount;

    private TransactionType transactionType;

}
