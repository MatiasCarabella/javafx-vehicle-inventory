package com.vehicleapp.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.vehicleapp.repository.VehicleRepository;
import com.vehicleapp.service.VehicleService;

public class AppModule extends AbstractModule {
  @Override
  protected void configure() {
    // Bind repository and service as singletons
    bind(VehicleRepository.class).in(Scopes.SINGLETON);
    bind(VehicleService.class).in(Scopes.SINGLETON);
  }
}
