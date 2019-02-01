package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GlobalPosition {

    private Account account;
    private Mortage mortage;
    private List<Loan> loans;
}
