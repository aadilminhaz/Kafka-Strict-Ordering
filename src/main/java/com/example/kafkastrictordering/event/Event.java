package com.example.kafkastrictordering.event;

import lombok.Data;

@Data
public abstract class Event {
    private Long eventId;
}
