package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.Account;
import com.alan.developer.demoreactivew.model.GlobalPosition;
import com.alan.developer.demoreactivew.model.Loan;
import com.alan.developer.demoreactivew.model.Mortage;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log
@Service
public class AccountService {

    final Random random = new Random(System.currentTimeMillis());

    public Flux<Account> findByOwnerId(final String ownerId) {
        final List<Account> accounts = new ArrayList<>();
        IntStream.range(0, 10).forEach(x ->
                accounts.add(Account.builder().Id(String.valueOf(x)).ownerId("500").amountAvailabe(new Random().nextDouble()).build())
        );
        return Flux.fromIterable(accounts);
    }

    public Mono<GlobalPosition> globalPositionSync(final Account account) {
        final List<Loan> loans = new ArrayList<>();
        log.info("Requesting loans");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        IntStream.range(0, 10).forEach(i ->
                loans.add(Loan.builder().ownerId(account.getOwnerId())
                        .amount(450D)
                        .creationDate(LocalDateTime.now())
                        .years(10).build()));
        log.info("Requesting mortage");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Mortage mortage = Mortage.builder()
                .amount(20000D)
                .creationDate(LocalDateTime.now())
                .ownerId(account.getOwnerId())
                .years(30).build();
        return Mono.just(GlobalPosition.builder()
                .account(account)
                .loans(loans)
                .mortage(mortage)
                .build());
    }

    public Mono<GlobalPosition> globalPositionReactive(final Account account) {
        Mono<Mortage> mMortage = findMortageByOwnerId(account);
        Flux<Loan> mLoans = findLoansByOwnerId(account);
        log.info("requesting mortage and loans");
        List<Loan> lLoans = mLoans.toStream().collect(Collectors.toList());
        Mortage m = mMortage.block();
        log.info("waiting for async elements");
        return Mono.just(GlobalPosition.builder()
                .account(account)
                .loans(lLoans)
                .mortage(m)
                .build());
    }

    public Flux<Loan> findLoansByOwnerId(final Account account) {
        log.info("Requesting loans");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Flux.fromIterable(IntStream.range(0, 10).mapToObj(i ->
                Loan.builder().ownerId(account.getOwnerId())
                        .amount(450D)
                        .creationDate(LocalDateTime.now())
                        .years(10).build()).collect(Collectors.toList()));
    }

    public Mono<Mortage> findMortageByOwnerId(final Account account) {
        log.info("Requesting mortage");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Mono.just(Mortage.builder()
                .amount(20000D)
                .creationDate(LocalDateTime.now())
                .ownerId(account.getOwnerId())
                .years(30).build());
    }
}
