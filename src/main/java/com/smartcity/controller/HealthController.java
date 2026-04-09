package com.smartcity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Health")
public class HealthController {

  @GetMapping("/health")
  @Operation(summary = "Backend health check")
  public Map<String, String> health() {
    return Map.of("status", "UP", "service", "smart-city-backend");
  }
}

