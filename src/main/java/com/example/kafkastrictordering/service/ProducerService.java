package com.example.kafkastrictordering.service;

public interface ProducerService<T> {

    public void sendEvent(T t) throws Exception;
}
