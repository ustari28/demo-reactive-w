package com.alan.developer.demoreactivew.web;

import com.alan.developer.demoreactivew.model.GlobalPosition;
import com.alan.developer.demoreactivew.model.Greeting;
import com.alan.developer.demoreactivew.service.AccountService;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Log
@RequestMapping("/v1/demo")
@RestController
public class DemoReactiveController {

    private Flowable flowable;
    @Autowired
    private AccountService accountService;

    private DemoReactiveController() {
        flowable = Flowable.fromCallable(() ->
                Greeting.builder().creationDate(LocalDateTime.now()).greeting("Hello").build()
        );
    }

    @GetMapping("parallel")
    public Mono<Void> parallel() {
        Flowable.range(1, 10)
                .flatMap(v -> {
                            log.info("Thread name " + Thread.currentThread().getName());
                            return Flowable.just(v)
                                    .subscribeOn(Schedulers.computation())
                                    .map(w -> multix(w));
                        }
                ).blockingSubscribe(x -> log.info("result:" + x));
        return Mono.empty();
    }

    @GetMapping("sync")
    public Mono<Void> sync() {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> multix(v))
                .blockingSubscribe(x -> log.info("result:" + x));
        return Mono.empty();
    }

    @GetMapping("account/parallel")
    public Flux<GlobalPosition> acountParallel() {

        /**ThreadPoolTaskExecutor tf = new ThreadPoolTaskExecutor();
         tf.setCorePoolSize(10);
         tf.setMaxPoolSize(20);
         tf.setQueueCapacity(40);
         tf.setDaemon(false);
         tf.setThreadGroupName("parallel-rx");
         tf.setKeepAliveSeconds(5);*/
        return Flux.create(s -> accountService.findByOwnerId("alan").subscribeOn(reactor.core.scheduler.Schedulers.newParallel("my-paralallel", 5))
                .flatMap(c -> {
                    System.out.println("Thread name " + Thread.currentThread().getName());
                    return Flux.just(c).subscribeOn(reactor.core.scheduler.Schedulers.parallel()).map(ac -> accountService.globalPositionReactive(ac));
                }).subscribe(f -> {
                    System.out.println("Thread name " + Thread.currentThread().getName() + " - account " + f.getAccount().getId());
                    s.next(f);
                    s.complete();
                })
        );
    }

    @GetMapping("account/sync")
    public Mono<Void> acountSync() {
        accountService.findByOwnerId("alan")
                .subscribeOn(reactor.core.scheduler.Schedulers.elastic())
                .map(c -> accountService.globalPositionSync(c))
                .blockLast();
        //.blockingSubscribe();
        return Mono.empty();
    }


    private Integer multix(Integer x) throws InterruptedException {
        Thread.sleep(1000);
        return x * x;
    }
}
