package com.example.workbench.web;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map health() {
        Map body = new LinkedHashMap();
        body.put("status", "UP");
        body.put("application", "java8-workbench");
        body.put("java", System.getProperty("java.version"));
        return body;
    }
}
