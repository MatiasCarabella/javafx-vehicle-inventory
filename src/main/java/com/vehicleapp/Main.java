package com.vehicleapp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vehicleapp.config.AppModule;
import com.vehicleapp.view.VehicleFXView;
import javafx.application.Application;
import lombok.Getter;

/**
 * Main entry point for the Vehicle Management System application. Initializes the Guice dependency
 * injection container and launches the JavaFX UI.
 */
public class Main {
  @Getter private static Injector injector;

  public static void main(String[] args) {
    // Initialize Guice
    injector = Guice.createInjector(new AppModule());

    // Launch JavaFX application
    Application.launch(VehicleFXView.class, args);
  }
}
