package com.alan.developer.demoreactivew.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskVue {
    private String owner;
    private String uuid;
    private String title;
    private String description;
    private Long start;
    private Long end;
    private Long duration;
    private Integer progress;
}
