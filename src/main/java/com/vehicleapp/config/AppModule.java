package com.vehicleapp.config;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.vehicleapp.controller.VehicleController;
import com.vehicleapp.repository.VehicleRepository;
import com.vehicleapp.service.VehicleService;
import com.vehicleapp.view.VehicleFXView;

/**
 * Guice module configuration for dependency injection. Configures all application components as
 * singletons.
 */
public class AppModule extends AbstractModule {
  @Override
  protected void configure() {
    // Bind main components as singletons
    bind(VehicleRepository.class).in(Scopes.SINGLETON);
    bind(VehicleService.class).in(Scopes.SINGLETON);
    bind(VehicleController.class).in(Scopes.SINGLETON);
    bind(VehicleFXView.class).in(Scopes.SINGLETON);
  }
}
