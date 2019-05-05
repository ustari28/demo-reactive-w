package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericError {
    private String message;
    private Integer status;
    private String description;
}
