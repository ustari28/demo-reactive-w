package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Loan {
    private String ownerId;
    private LocalDateTime creationDate;
    private Double amount;
    private Integer years;
}
