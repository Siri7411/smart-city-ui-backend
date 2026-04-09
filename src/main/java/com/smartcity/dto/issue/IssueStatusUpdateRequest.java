package com.smartcity.dto.issue;

import com.smartcity.model.IssueStatus;
import jakarta.validation.constraints.NotNull;

public class IssueStatusUpdateRequest {
  @NotNull
  private IssueStatus status;

  public IssueStatus getStatus() {
    return status;
  }

  public void setStatus(IssueStatus status) {
    this.status = status;
  }
}

