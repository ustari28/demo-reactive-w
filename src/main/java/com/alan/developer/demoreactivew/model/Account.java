package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Account {

    private String Id;
    private String iban;
    private String ownerId;
    private LocalDateTime creationDate;
    private Double amountAvailabe;
}
