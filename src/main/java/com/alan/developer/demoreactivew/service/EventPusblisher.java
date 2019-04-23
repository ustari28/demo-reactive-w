package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.LogEvent;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class EventPusblisher implements Publisher<LogEvent> {
    @Override
    public void subscribe(Subscriber<? super LogEvent> s) {

    }
}
