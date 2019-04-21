package com.alan.developer.demoreactivew.web;

import com.alan.developer.demoreactivew.model.EventVue;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vue/v1")
public class VueController {

    private List<EventVue> events = new ArrayList<>();
    private String[] titles = {
            "Rat eats little pig",
            "IntelliJ is better than eclipse",
            "Umbrella academy is awesome"
    };

    @CrossOrigin
    @GetMapping("/events")
    public List<EventVue> searchEvents() {
        LocalDateTime ldt = LocalDateTime.now();
        final Long date = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        final Long sd = System.currentTimeMillis();
        final Long filter = ldt.plusMinutes(-5).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        LocalDateTime sys = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        System.out.println(ldt.toString() + "->" + date + "->" + sd + "->" + sys.toString());
        events.add(EventVue.builder().idx(events.size() + 1)
                .title(titles[RandomUtils.nextInt(0, titles.length)])
                .date(date)
                .build());
        return events.parallelStream().filter(e -> e.getDate() > filter)
                .collect(Collectors.toList());

    }

}
