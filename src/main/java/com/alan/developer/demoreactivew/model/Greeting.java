package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Greeting {
    private String greeting;
    private LocalDateTime creationDate;
}
