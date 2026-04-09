package com.smartcity.service;

import com.smartcity.dto.city.CityServiceRequest;
import com.smartcity.dto.city.CityServiceResponse;
import com.smartcity.exception.BadRequestException;
import com.smartcity.model.CityService;
import com.smartcity.repository.CityServiceRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CityServiceManager {
  private final CityServiceRepository repository;
  private final ModelMapper modelMapper;

  public CityServiceManager(CityServiceRepository repository, ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  public List<CityServiceResponse> getAll() {
    return repository.findAll().stream()
        .map(entity -> modelMapper.map(entity, CityServiceResponse.class))
        .toList();
  }

  public CityServiceResponse create(CityServiceRequest request) {
    CityService entity = modelMapper.map(request, CityService.class);
    CityService saved = repository.save(entity);
    return modelMapper.map(saved, CityServiceResponse.class);
  }

  public CityServiceResponse update(Long id, CityServiceRequest request) {
    CityService existing =
        repository.findById(id).orElseThrow(() -> new BadRequestException("Service not found"));
    existing.setName(request.getName());
    existing.setType(request.getType());
    existing.setStatus(request.getStatus());
    existing.setDescription(request.getDescription());
    CityService saved = repository.save(existing);
    return modelMapper.map(saved, CityServiceResponse.class);
  }

  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new BadRequestException("Service not found");
    }
    repository.deleteById(id);
  }
}

