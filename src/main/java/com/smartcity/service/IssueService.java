package com.smartcity.service;

import com.smartcity.dto.issue.IssueRequest;
import com.smartcity.dto.issue.IssueResponse;
import com.smartcity.dto.issue.IssueStatusUpdateRequest;
import com.smartcity.exception.BadRequestException;
import com.smartcity.model.Issue;
import com.smartcity.model.IssueStatus;
import com.smartcity.repository.IssueRepository;
import java.time.Instant;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
  private final IssueRepository repository;
  private final ModelMapper modelMapper;

  public IssueService(IssueRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  public List<IssueResponse> getAll() {
    return repository.findAll().stream()
        .map(entity -> modelMapper.map(entity, IssueResponse.class))
        .toList();
  }

  public IssueResponse create(String reportedBy, IssueRequest request) {
    Issue issue = modelMapper.map(request, Issue.class);
    issue.setReportedBy(reportedBy);
    issue.setStatus(IssueStatus.PENDING);
    issue.setCreatedAt(Instant.now());
    Issue saved = repository.save(issue);
    return modelMapper.map(saved, IssueResponse.class);
  }

  public IssueResponse updateStatus(Long id, IssueStatusUpdateRequest request) {
    Issue issue = repository.findById(id).orElseThrow(() -> new BadRequestException("Issue not found"));
    issue.setStatus(request.getStatus());
    Issue saved = repository.save(issue);
    return modelMapper.map(saved, IssueResponse.class);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new BadRequestException("Issue not found");
    }
    repository.deleteById(id);
  }
}

