package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.LogEvent;
import lombok.extern.java.Log;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Log
public class EventConsumer implements Subscriber<LogEvent> {
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        subscription.request(Integer.MAX_VALUE);
    }

    @Override
    public void onNext(LogEvent logEvent) {
        log.info("Log event -> " + logEvent.toString());
        //subscription.request(10);
    }

    @Override
    public void onError(Throwable t) {
        log.severe("error");
    }

    @Override
    public void onComplete() {
        log.info("Finishing ...");
        subscription.cancel();
    }
}
