package com.f1hub.f1communityhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public  Map<String, String> health() {
        return Map.of("status", "UP");
    }


}
