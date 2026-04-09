package com.smartcity.config;

import com.smartcity.model.CityService;
import com.smartcity.repository.CityServiceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner seedServices(CityServiceRepository cityServiceRepository) {
    return args -> {
      if (cityServiceRepository.count() == 0) {
        CityService water = new CityService();
        water.setName("City Main Water Supply");
        water.setType("Utility");
        water.setStatus("Online");
        water.setDescription("Monitors live water distribution and pressure.");

        CityService power = new CityService();
        power.setName("Downtown Power Grid");
        power.setType("Electric");
        power.setStatus("Maintenance");
        power.setDescription("Smart electric grid with outage monitoring.");

        CityService transport = new CityService();
        transport.setName("Metro Line A");
        transport.setType("Transport");
        transport.setStatus("Online");
        transport.setDescription("Public transit corridor connecting key hubs.");

        cityServiceRepository.save(water);
        cityServiceRepository.save(power);
        cityServiceRepository.save(transport);
      }
    };
  }
}

