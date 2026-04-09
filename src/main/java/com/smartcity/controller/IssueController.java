package com.smartcity.controller;

import com.smartcity.dto.issue.IssueRequest;
import com.smartcity.dto.issue.IssueResponse;
import com.smartcity.dto.issue.IssueStatusUpdateRequest;
import com.smartcity.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
@Tag(name = "Issues")
public class IssueController {
  private final IssueService issueService;

  public IssueController(IssueService issueService) {
    this.issueService = issueService;
  }

  @GetMapping("/issues")
  @Operation(summary = "Get all reported issues")
  public List<IssueResponse> getAll() {
    return issueService.getAll();
  }

  @PostMapping("/issues")
  @Operation(summary = "Report a new issue")
  public ResponseEntity<IssueResponse> create(
      @Valid @RequestBody IssueRequest request, Authentication authentication) {
    String reportedBy = authentication.getName();
    return ResponseEntity.status(HttpStatus.CREATED).body(issueService.create(reportedBy, request));
  }

  @PutMapping("/admin/issues/{id}/status")
  @Operation(summary = "Update issue status (admin)")
  public IssueResponse updateStatus(@PathVariable Long id, @Valid @RequestBody IssueStatusUpdateRequest request) {
    return issueService.updateStatus(id, request);
  }

  @DeleteMapping("/admin/issues/{id}")
  @Operation(summary = "Delete issue (admin)")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    issueService.delete(id);
    return ResponseEntity.noContent().build();
  }
}

