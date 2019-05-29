package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenJwt {
    private String token;
}
