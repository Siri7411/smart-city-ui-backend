package com.smartcity.dto.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CityServiceRequest {
  @NotBlank
  @Size(max = 120)
  private String name;

  @NotBlank
  @Size(max = 80)
  private String type;

  @NotBlank
  @Size(max = 80)
  private String status;

  @Size(max = 300)
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

