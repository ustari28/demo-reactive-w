package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventVue {

    private Integer idx;
    private String title;
    private Long date;
}
