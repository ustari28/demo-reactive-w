package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.Account;
import com.alan.developer.demoreactivew.model.GlobalPosition;
import com.alan.developer.demoreactivew.model.Loan;
import com.alan.developer.demoreactivew.model.Mortage;
import io.reactivex.Flowable;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log
@Service
public class AccountService {

    final Random random = new Random(System.currentTimeMillis());

    public Flowable<Account> findByOwnerId(final String ownerId) {
        final List<Account> accounts = new ArrayList<>();
        IntStream.range(0, 10).forEach(x ->
                accounts.add(Account.builder().Id(String.valueOf(x)).ownerId("500").amountAvailabe(new Random().nextDouble()).build())
        );
        return Flowable.fromIterable(accounts);
    }

    public GlobalPosition globalPositionSync(final Account account) {
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
        return GlobalPosition.builder()
                .account(account)
                .loans(loans)
                .mortage(mortage)
                .build();
    }

    public GlobalPosition globalPositionReactive(final Account account) throws ExecutionException, InterruptedException {
        CompletableFuture<Mortage> cMortage = CompletableFuture.supplyAsync(() -> findMortageByOwnerId(account));
        CompletableFuture<List<Loan>> cLoans = CompletableFuture.supplyAsync(() -> findLoansByOwnerId(account));
        CompletableFuture.allOf(cMortage).get();
        return GlobalPosition.builder()
                .account(account)
                .loans(cLoans.get())
                .mortage(cMortage.get())
                .build();
    }

    public List<Loan> findLoansByOwnerId(final Account account) {
        log.info("Requesting loans");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return IntStream.range(0, 10).mapToObj(i ->
                Loan.builder().ownerId(account.getOwnerId())
                        .amount(450D)
                        .creationDate(LocalDateTime.now())
                        .years(10).build()).collect(Collectors.toList());
    }

    public Mortage findMortageByOwnerId(final Account account) {
        log.info("Requesting mortage");
        try {
            Thread.sleep(random.nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Mortage.builder()
                .amount(20000D)
                .creationDate(LocalDateTime.now())
                .ownerId(account.getOwnerId())
                .years(30).build();
    }
}
