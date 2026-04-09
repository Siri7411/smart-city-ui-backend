package com.smartcity.dto.issue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IssueRequest {
  @NotBlank
  @Size(max = 80)
  private String issueType;

  @NotBlank
  @Size(max = 500)
  private String description;

  @NotBlank
  @Size(max = 120)
  private String location;

  public String getIssueType() {
    return issueType;
  }

  public void setIssueType(String issueType) {
    this.issueType = issueType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}

