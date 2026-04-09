package com.smartcity.controller;

import com.smartcity.dto.city.CityServiceRequest;
import com.smartcity.dto.city.CityServiceResponse;
import com.smartcity.service.CityServiceManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "City Services")
public class CityServiceController {
  private final CityServiceManager cityServiceManager;

  public CityServiceController(CityServiceManager cityServiceManager) {
    this.cityServiceManager = cityServiceManager;
  }

  @GetMapping("/services")
  @Operation(summary = "Get all city services")
  public List<CityServiceResponse> getAllServices() {
    return cityServiceManager.getAll();
  }

  @PostMapping("/admin/services")
  @Operation(summary = "Create city service (admin)")
  public ResponseEntity<CityServiceResponse> createService(@Valid @RequestBody CityServiceRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(cityServiceManager.create(request));
  }

  @PutMapping("/admin/services/{id}")
  @Operation(summary = "Update city service (admin)")
  public CityServiceResponse updateService(
      @PathVariable Long id, @Valid @RequestBody CityServiceRequest request) {
    return cityServiceManager.update(id, request);
  }

  @DeleteMapping("/admin/services/{id}")
  @Operation(summary = "Delete city service (admin)")
  public ResponseEntity<Void> deleteService(@PathVariable Long id) {
    cityServiceManager.delete(id);
    return ResponseEntity.noContent().build();
  }
}

